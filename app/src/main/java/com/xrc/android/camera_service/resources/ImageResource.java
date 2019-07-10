package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.CameraController;
import com.xrc.android.camera_service.Factory;
import com.xrc.restlet.MediaType;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ImageResource extends ServerResource {

    public static final String PATH = "/image";

    @Get(MediaType.IMAGE_JPEG)
    ByteArrayRepresentation getImage() {

        CameraController cameraController = Factory.getCameraController();
        byte[] image = cameraController.captureJPEGImage();

        return new ByteArrayRepresentation(image, org.restlet.data.MediaType.IMAGE_JPEG);
    }

}

