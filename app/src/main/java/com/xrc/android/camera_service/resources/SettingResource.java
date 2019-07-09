package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.resources.beans.Setting;
import com.xrc.restlet.MediaType;
import com.xrc.restlet.RestletResourceUtil;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;


public class SettingResource extends ServerResource {

    private static final String SETTING_PATH_PARAM = "setting";

    public static final String PATH = String.format("/settings/{%s}", SETTING_PATH_PARAM);

    private final RestletResourceUtil resourceUtil = new RestletResourceUtil(this);

    @Get(MediaType.APPLICATION_JSON)
    Setting getSettingInfo() throws ResourceException {
        throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
    }

}

