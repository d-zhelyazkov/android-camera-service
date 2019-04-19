package com.xrc.android.camera_service.resource;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.xrc.restlet.MediaType;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.io.ByteArrayOutputStream;

public class ImageResource extends ServerResource {

    public static final String PATH = "/image";

    public static Drawable dummyImage;

    @Get(MediaType.IMAGE_JPEG)
    ByteArrayRepresentation getImage() throws ResourceException {
        Bitmap bitmap = ((BitmapDrawable) dummyImage).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return new ByteArrayRepresentation(stream.toByteArray(),
                org.restlet.data.MediaType.IMAGE_JPEG);
    }

}

