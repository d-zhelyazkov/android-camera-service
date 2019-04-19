package com.xrc.android.camera_service.resource;

import com.xrc.restlet.MediaType;
import com.xrc.restlet.RestletResourceUtil;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.logging.Logger;


public class SettingResource extends ServerResource {

    private static final String SETTING_PATH_PARAM = "setting";

    public static final String PATH = String.format("/settings/{%s}", SETTING_PATH_PARAM);

    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    private final RestletResourceUtil resourceUtil = new RestletResourceUtil(this);

    @Get(MediaType.APPLICATION_JSON)
    SettingValue getSettingValue() throws ResourceException {
        String setting = resourceUtil.getRequestAttributeStr(SETTING_PATH_PARAM);
        return new SettingValue(setting + ": 21MP");
    }

    @Put(MediaType.APPLICATION_JSON)
    void updateSettingValue(SettingValue settingValue) throws ResourceException {
        String setting = resourceUtil.getRequestAttributeStr(SETTING_PATH_PARAM);
        logger.info(setting + ": " + settingValue);
    }

}

