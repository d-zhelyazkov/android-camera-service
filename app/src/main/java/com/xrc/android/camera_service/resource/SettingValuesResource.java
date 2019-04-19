package com.xrc.android.camera_service.resource;

import com.xrc.restlet.MediaType;
import com.xrc.restlet.RestletResourceUtil;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class SettingValuesResource extends ServerResource {

    private static final String SETTING_PATH_PARAM = "setting";

    public static final String PATH = String.format("/settings/{%s}/values", SETTING_PATH_PARAM);

    private final RestletResourceUtil resourceUtil = new RestletResourceUtil(this);

    @Get(MediaType.APPLICATION_JSON)
    String[] getSettingValues() throws ResourceException {
        String setting = resourceUtil.getRequestAttributeStr(SETTING_PATH_PARAM);
        throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Setting not found - " + setting);
    }

}

