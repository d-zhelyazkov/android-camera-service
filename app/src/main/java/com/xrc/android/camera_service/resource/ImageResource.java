package com.xrc.android.camera_service.resource;

import com.xrc.restlet.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ImageResource extends ServerResource {

    public static final String PATH = "/image";

    @Get(MediaType.IMAGE_JPEG)
    ByteArrayRepresentation getImage() {
        throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
    }

}

