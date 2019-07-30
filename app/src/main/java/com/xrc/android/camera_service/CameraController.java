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
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.util.Size;
import android.view.Surface;
import com.xrc.android.example.AutoFitTextureView;
import com.xrc.android.example.CameraTextureTransformer;
import com.xrc.android.example.SizeAreaComparator;
import com.xrc.android.os.Handlers;
import com.xrc.android.view.AbstractSurfaceTextureListener;
import com.xrc.lang.CloseableUtils;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

public class CameraController {

    private static final Size OPTIMAL_PREVIEW_SIZE = new Size(1280, 720);

    private final int cameraType;

    private CameraManager cameraManager;

    private Size previewSize;

    private final Handler backgroundHandler = Handlers.getNewThreadHandler("camera_background_thread");

    private CameraDevice cameraDevice;

    private AutoFitTextureView cameraView;

    private CameraCaptureSession cameraSession;

    private CaptureRequest.Builder previewRequest;

    private ImageReader captureImageReader;

    private CaptureRequest.Builder captureRequest;

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
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
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

    public void captureImage() {
        try {
            cameraSession.capture(captureRequest.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Observable<byte[]> getCaptureImageObservable() {
        return captureImageObservable;
    }

    private void setUpCamera() throws CameraAccessException, SecurityException, InterruptedException {
        String cameraId = findCameraId();

        CameraCharacteristics cameraCharacteristics =
                cameraManager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap streamConfigurationMap =
                cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        assert (streamConfigurationMap != null);

        Size[] outputSizes = streamConfigurationMap.getOutputSizes(SurfaceTexture.class);
        previewSize = resolveOptimalPreviewSize(outputSizes);
        Handlers.getMainThreadHandler().post(() -> cameraView.setAspectRatio(previewSize));

        CameraTextureTransformer cameraTextureTransformer =
                new CameraTextureTransformer(cameraView, previewSize, displayRotation);
        cameraTextureTransformer.configureTransform(cameraView.getWidth(), cameraView.getHeight());

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
        surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
        Surface previewSurface = new Surface(surfaceTexture);
        previewRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        previewRequest.addTarget(previewSurface);

        captureImageReader = ImageReader.newInstance(previewSize.getWidth(), previewSize.getHeight(),
                ImageFormat.JPEG, /*maxImages*/1);

        Observable<byte[]> coldImageObservable = Observable.create(emitter ->
                captureImageReader.setOnImageAvailableListener(reader -> {
                    try (Image image = reader.acquireLatestImage()) {
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);

                        emitter.onNext(bytes);
                    }
                },
                backgroundHandler));
        captureImageObservable = PublishSubject.create();
        coldImageObservable.subscribe(captureImageObservable);

        Surface captureSurface = captureImageReader.getSurface();
        captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        captureRequest.addTarget(captureSurface);

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

    private void setRepeatingPreviewRequest() throws CameraAccessException {

        cameraSession.setRepeatingRequest(
                previewRequest.build(), null, backgroundHandler);
    }

    private Size resolveOptimalPreviewSize(Size[] previewSizes) {

        Optional<Size> optionalSize = Arrays.stream(previewSizes)
                .filter(size ->
                        ((size.getWidth() <= OPTIMAL_PREVIEW_SIZE.getWidth())
                                && (size.getHeight() <= OPTIMAL_PREVIEW_SIZE.getHeight())))
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

    /**
     * Closes the current {@link CameraDevice}.
     */
    private void closeCamera() {
        CloseableUtils.closeQuietly(cameraSession);
        CloseableUtils.closeQuietly(cameraDevice);
        CloseableUtils.closeQuietly(captureImageReader);
    }

}
