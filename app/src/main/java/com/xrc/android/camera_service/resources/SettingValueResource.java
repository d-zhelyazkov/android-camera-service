package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.resources.beans.SettingValue;
import com.xrc.restlet.MediaType;
import com.xrc.restlet.RestletResourceUtil;
import org.restlet.data.Status;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class SettingValueResource extends ServerResource {

    private static final String SETTING_PATH_PARAM = "setting";

    public static final String PATH = String.format("/settings/{%s}/value", SETTING_PATH_PARAM);

    private final RestletResourceUtil resourceUtil = new RestletResourceUtil(this);

    @Put(MediaType.APPLICATION_JSON)
    void updateSettingValue(SettingValue settingValue) throws ResourceException {

        throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);

    }

}

