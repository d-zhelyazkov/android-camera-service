package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;

import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;

public abstract class BaseSettingController<T>
        implements CameraSettingController<T> {

    protected final CameraController cameraController;
    protected final CaptureResult.Key<T> resultKey;
    protected final CaptureRequest.Key<T> requestKey;
    protected final CameraSetting cameraSetting;

    protected BaseSettingController(
            CameraController cameraController,
            CaptureResult.Key<T> resultKey,
            CaptureRequest.Key<T> requestKey,
            CameraSetting cameraSetting) {
        this.cameraController = cameraController;
        this.resultKey = resultKey;
        this.requestKey = requestKey;
        this.cameraSetting = cameraSetting;
    }


    @Override
    public T getValue() {
        return cameraController.getCaptureResultValue(resultKey);
    }

    @Override
    public void setValue(T value) {
        cameraController.setCaptureRequestValue(requestKey, value);
    }


    @Override
    public CameraSetting getControlledSetting() {
        return this.cameraSetting;
    }

    @Override
    public String getDisplayValue() throws UnsupportedSettingException {
        return String.valueOf(this.getValue());
    }

}
