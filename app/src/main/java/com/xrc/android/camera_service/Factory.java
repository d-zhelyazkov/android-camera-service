package com.xrc.android.camera_service;

import android.hardware.camera2.CameraCharacteristics;

public class Factory {

    private static final CameraController cameraController =
            new CameraController(CameraCharacteristics.LENS_FACING_BACK);

    public static CameraController getCameraController() {
        return cameraController;
    }
}
