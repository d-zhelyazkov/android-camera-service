package com.xrc.android.camera_service.resources;

import com.xrc.restlet.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class SettingsResource extends ServerResource {

    public static final String PATH = "/settings";

    @Get(MediaType.APPLICATION_JSON)
    String[] getSettings() throws ResourceException {
        return new String[] {};
    }

}

