package com.xrc.android.camera_service;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;

import com.xrc.android.example.AutoFitTextureView;
import com.xrc.android.example.CameraTextureTransformer;
import com.xrc.android.example.SizeAreaComparator;
import com.xrc.android.os.Handlers;
import com.xrc.android.view.AbstractSurfaceTextureListener;
import com.xrc.lang.CloseableUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CameraController implements com.xrc.android.hardware.camera2.CameraController {

    private static final Size OPTIMAL_RES = new Size(960, 540);

    private final int cameraType;

    private final AtomicReference<CaptureResult> captureResultRef = new AtomicReference<>();

    private CameraManager cameraManager;

    private Size resolution;

    private final Handler backgroundHandler = Handlers.getNewThreadHandler(
            "camera_background_thread");

    private CameraDevice cameraDevice;

    private AutoFitTextureView cameraView;

    private CameraCaptureSession cameraSession;

    private CaptureRequest.Builder previewRequest;

    private ImageReader captureImageReader;

    private PublishSubject<byte[]> captureImageObservable;

    private int displayRotation;

    CameraController(int cameraType) {
        this.cameraType = cameraType;
    }

    void init(Activity activity) {

        cameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        cameraView = activity.findViewById(R.id.camera_view);
        displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
    }

    void startPreview() throws CameraAccessException, InterruptedException {

        if (!cameraView.isAvailable()) {
            CountDownLatch viewAvailable = new CountDownLatch(1);
            cameraView.setSurfaceTextureListener(new AbstractSurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width,
                                                      int height) {
                    viewAvailable.countDown();
                }
            });
            viewAvailable.await();
        }

        setUpCamera();
    }

    void stopPreview() {

        closeCamera();
    }

    public Observable<byte[]> getCaptureImageObservable() {
        return captureImageObservable;
    }

    @Override
    public <T> T getCaptureResultValue(CaptureResult.Key<T> resultKey) {
        CaptureResult captureResult = captureResultRef.get();
        return captureResult.get(resultKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void setCaptureRequestValue(CaptureRequest.Key<T> requestKey, T value) {
        setCaptureRequestValues(new Pair[]{new Pair<>(requestKey, value)});
    }

    @Override
    public void setCaptureRequestValues(Pair<CaptureRequest.Key<Object>, Object>[] values) {
        try {

            for (Pair<CaptureRequest.Key<Object>, Object> value : values) {
                setRequestValueInternal(value);
            }

            stopRepeatingPreviewRequest();
            setRepeatingPreviewRequest();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T getCameraCharacteristic(CameraCharacteristics.Key<T> characteristicKey) {
        try {
            CameraCharacteristics cameraCharacteristics =
                    cameraManager.getCameraCharacteristics(cameraDevice.getId());
            return cameraCharacteristics.get(characteristicKey);

        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Size getResolution() {
        return resolution;
    }

    private void setUpCamera() throws CameraAccessException, SecurityException,
            InterruptedException {
        String cameraId = findCameraId();

        CameraCharacteristics cameraCharacteristics =
                cameraManager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap streamConfigurationMap =
                cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        assert (streamConfigurationMap != null);

        Size[] outputSizes = streamConfigurationMap.getOutputSizes(SurfaceTexture.class);
        resolution = resolveResolution(outputSizes);

        Handlers.getMainThreadHandler().post(() -> {
            cameraView.setAspectRatio(resolution);
            new CameraTextureTransformer(cameraView, resolution, displayRotation)
                    .configureTransform(cameraView.getWidth(), cameraView.getHeight());
        });

        CountDownLatch cameraOpened = new CountDownLatch(1);
        cameraManager.openCamera(
                cameraId,
                new CameraDevice.StateCallback() {
                    @Override
                    public void onOpened(CameraDevice device) {
                        cameraDevice = device;

                        cameraOpened.countDown();
                    }

                    @Override
                    public void onDisconnected(CameraDevice device) {
                        throw new RuntimeException("Camera disconnected.");
                    }

                    @Override
                    public void onError(CameraDevice device, int error) {
                        throw new RuntimeException("Camera error: " + error);
                    }
                },
                backgroundHandler);
        cameraOpened.await();

        createPreviewSession();

    }

    private void createPreviewSession() throws CameraAccessException, InterruptedException {
        SurfaceTexture surfaceTexture = cameraView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(resolution.getWidth(), resolution.getHeight());
        Surface previewSurface = new Surface(surfaceTexture);
        previewRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        previewRequest.addTarget(previewSurface);

        captureImageReader = ImageReader.newInstance(resolution.getWidth(),
                resolution.getHeight(),
                ImageFormat.JPEG, /*maxImages*/2);
        Surface captureSurface = captureImageReader.getSurface();
        previewRequest.addTarget(captureSurface);

        Observable<byte[]> coldImageObservable = Observable.create(emitter ->
                captureImageReader.setOnImageAvailableListener(reader -> {
                            try (Image image = reader.acquireNextImage()) {
                                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);

                                emitter.onNext(bytes);
                            }
                        },
                        backgroundHandler));
        captureImageObservable = PublishSubject.create();
        coldImageObservable.subscribe(captureImageObservable);

        CountDownLatch sessionCreated = new CountDownLatch(1);
        cameraDevice.createCaptureSession(
                Arrays.asList(previewSurface, captureSurface),
                new CameraCaptureSession.StateCallback() {

                    @Override
                    public void onConfigured(CameraCaptureSession captureSession) {
                        cameraSession = captureSession;

                        sessionCreated.countDown();
                    }

                    @Override
                    public void onConfigureFailed(CameraCaptureSession captureSession) {
                        throw new RuntimeException("Camera session configuration failed.");
                    }
                },
                backgroundHandler);
        sessionCreated.await();

        setRepeatingPreviewRequest();
    }

    private void setRepeatingPreviewRequest() throws CameraAccessException, InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        cameraSession.setRepeatingRequest(
                previewRequest.build(),
                new CameraCaptureSession.CaptureCallback() {

                    @Override
                    public void onCaptureCompleted(
                            CameraCaptureSession session, CaptureRequest request,
                            TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);

                        latch.countDown();

                        captureResultRef.set(result);
                    }
                },
                backgroundHandler);
        latch.await();
    }

    private void stopRepeatingPreviewRequest() throws CameraAccessException {
        cameraSession.stopRepeating();
        cameraSession.abortCaptures();
    }

    private Size resolveResolution(Size[] previewSizes) {

        Optional<Size> optionalSize = Arrays.stream(previewSizes)
                .filter(size ->
                        ((size.getWidth() <= OPTIMAL_RES.getWidth())
                                && (size.getHeight() <= OPTIMAL_RES.getHeight())))
                .max(new SizeAreaComparator());
        if (!optionalSize.isPresent())
            throw new RuntimeException("Cannot resolve optimal preview size.");

        return optionalSize.get();
    }

    private String findCameraId() throws CameraAccessException {

        for (String cameraId : cameraManager.getCameraIdList()) {
            try {
                CameraCharacteristics cameraCharacteristics =
                        cameraManager.getCameraCharacteristics(cameraId);

                Integer cameraType = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);

                if (Objects.equals(cameraType, this.cameraType))
                    return cameraId;

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Camera not found.");
    }

    private <T> void setRequestValueInternal(Pair<CaptureRequest.Key<T>, T> requestValue) {
        CaptureRequest.Key<T> requestKey = requestValue.first;
        T value = requestValue.second;
        if (requestKey == CaptureRequest.CONTROL_AF_MODE) {
            setFocusMode((Integer) value);
        } else {
            previewRequest.set(requestKey, value);
        }
    }

    private void setFocusMode(int focusMode) {
        switch (focusMode) {
            case CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE:
            case CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO:
                previewRequest.set(CaptureRequest.CONTROL_AF_MODE,
                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO);
                break;
            default:
                previewRequest.set(CaptureRequest.CONTROL_AF_MODE, focusMode);

        }
    }

    /**
     * Closes the current {@link CameraDevice}.
     */
    private void closeCamera() {
        CloseableUtils.closeQuietly(cameraSession);
        CloseableUtils.closeQuietly(cameraDevice);
        CloseableUtils.closeQuietly(captureImageReader);
    }

}
