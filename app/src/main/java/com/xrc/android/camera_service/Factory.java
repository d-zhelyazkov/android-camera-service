package com.xrc.android.camera_service;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Handler;
import com.xrc.android.os.Handlers;
import com.xrc.android.hardware.camera2.settings.CameraSettingsManager;


public class Factory {

    private static final CameraController cameraController =
            new CameraController(CameraCharacteristics.LENS_FACING_BACK);

    private static final CameraSettingsManager settingsManager = new CameraSettingsManager(cameraController);

    private static final Handler secondaryThreadHandler = Handlers.getNewThreadHandler("secondary_thread");

    public static CameraController getCameraController() {
        return cameraController;
    }

    public static CameraSettingsManager getCameraSettingsManager() {
        return settingsManager;
    }

    static Handler getSecondaryThreadHandler() {
        return secondaryThreadHandler;
    }

}
