package com.xrc.android.camera_service.resource;

import com.xrc.restlet.MediaType;
import com.xrc.restlet.RestletResourceUtil;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;


public class SettingResource extends ServerResource {

    private static final String SETTING_PATH_PARAM = "setting";

    public static final String PATH = String.format("/settings/{%s}", SETTING_PATH_PARAM);

    private final RestletResourceUtil resourceUtil = new RestletResourceUtil(this);

    @Get(MediaType.APPLICATION_JSON)
    SettingValue getSettingValue() throws ResourceException {
        String setting = resourceUtil.getRequestAttributeStr(SETTING_PATH_PARAM);
        throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Setting not found - " + setting);
    }

    @Put(MediaType.APPLICATION_JSON)
    void updateSettingValue(SettingValue settingValue) throws ResourceException {
        String setting = resourceUtil.getRequestAttributeStr(SETTING_PATH_PARAM);
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                String.format("Invalid value '%s' for setting '%s'.", settingValue.getValue(), setting));
    }

}

