package com.xrc.android.camera_service;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Handler;
import com.xrc.android.os.Handlers;


public class Factory {

    private static final CameraController cameraController =
            new CameraController(CameraCharacteristics.LENS_FACING_BACK);

    private static final Handler secondaryThreadHandler = Handlers.getNewThreadHandler("secondary_thread");

    public static CameraController getCameraController() {
        return cameraController;
    }

    static Handler getSecondaryThreadHandler() {
        return secondaryThreadHandler;
    }

}
