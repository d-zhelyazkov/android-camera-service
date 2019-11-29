package com.xrc.android.camera_service.resources.util;

import com.xrc.android.camera_service.Factory;
import com.xrc.android.camera_service.resources.representation.Setting;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.CameraSettingsManager;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

public class CameraResourceUtil {

    public static CameraSettingController<String> getStringSettingController(Setting setting) throws ResourceException {

        CameraSetting cameraSetting = setting.getCameraSetting();
        CameraSettingsManager cameraSettingsManager = Factory.getCameraSettingsManager();
        CameraSettingController<String> settingController =
                cameraSettingsManager.getStringSettingController(cameraSetting);
        if (!settingController.isSettingSupported())
            throw newUnavailableSettingException();

        return settingController;
    }

    public static <T> CameraSettingController<T> getSettingController(CameraSetting cameraSetting) {
        CameraSettingsManager cameraSettingsManager = Factory.getCameraSettingsManager();
        CameraSettingController<T> settingController =
                cameraSettingsManager.getSettingController(cameraSetting);
        if (!settingController.isSettingSupported())
            throw newUnavailableSettingException();

        return settingController;
    }

    private static ResourceException newUnavailableSettingException() {
        return new ResourceException(
                Status.CLIENT_ERROR_NOT_FOUND, "The specified setting is not available.");

    }

}
