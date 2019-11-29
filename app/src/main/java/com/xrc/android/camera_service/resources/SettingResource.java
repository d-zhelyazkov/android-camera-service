package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.resources.representation.SettingInfo;
import com.xrc.android.camera_service.resources.representation.Setting;
import com.xrc.android.camera_service.resources.representation.SettingValue;
import com.xrc.android.camera_service.resources.util.CameraResourceUtil;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.exceptions.UneditableSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingValueException;
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

    @Get(MediaType.APPLICATION_JSON)
    public SettingInfo<String> getSettingInfo() throws ResourceException {
        CameraSettingController<String> settingController = getRequestedSettingController();
        return new SettingInfo<>(
                Setting.fromCameraSetting(settingController.getControlledSetting()),
                settingController.isEditable(),
                settingController.getValue(),
                settingController.getValues()
                        .toArray(String[]::new));

    }

    @Put(MediaType.APPLICATION_JSON)
    public void updateSettingValue(SettingValue settingValue) throws ResourceException {

        try {
            CameraSettingController<String> settingController = getRequestedSettingController();
            settingController.setValue(settingValue.getValue());

        } catch (UnsupportedSettingValueException e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid value provided.");
        } catch (UneditableSettingException e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "The value of the setting cannot be edited.");
        }

    }

    private CameraSettingController<String> getRequestedSettingController() {
        Setting setting = getRequestedSetting();

        return CameraResourceUtil.getStringSettingController(setting);
    }

    private Setting getRequestedSetting() {
        RestletResourceUtil resourceUtil = new RestletResourceUtil(this);
        String setting = resourceUtil.getRequestAttributeStr(SETTING_PATH_PARAM);
        try {
            return Setting.valueOf(setting);
        } catch (IllegalArgumentException e) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Setting not supported - " + setting);
        }
    }
}

