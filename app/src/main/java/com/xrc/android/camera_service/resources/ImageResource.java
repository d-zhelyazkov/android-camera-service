package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.CameraController;
import com.xrc.android.camera_service.Factory;
import com.xrc.reactivex.disposables.AutoDisposable;
import com.xrc.restlet.MediaType;
import io.reactivex.Observable;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class ImageResource extends ServerResource {

    public static final String PATH = "/image";

    @Get(MediaType.IMAGE_JPEG)
    ByteArrayRepresentation getImage() {

        AtomicReference<ByteArrayRepresentation> response = new AtomicReference<>();
        CameraController cameraController = Factory.getCameraController();
        Observable<byte[]> captureImageObservable = cameraController.getCaptureImageObservable();
        CountDownLatch imageRetrieved = new CountDownLatch(1);

        try (AutoCloseable subscription = new AutoDisposable(
                captureImageObservable.subscribe(bytes -> {
                    response.set(new ByteArrayRepresentation(
                            bytes, org.restlet.data.MediaType.IMAGE_JPEG));
                    imageRetrieved.countDown();
                }))) {

            cameraController.captureImage();
            imageRetrieved.await();

            return response.get();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

