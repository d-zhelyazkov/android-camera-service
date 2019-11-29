package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.lang.Booleans;

import java.util.stream.Stream;

public class AutoExposureLockController implements CameraSettingController<Boolean> {

    private final CameraController cameraController;

    public AutoExposureLockController(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    @Override
    public boolean isSettingSupported() {
        return cameraController.getCameraCharacteristic(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE);
    }

    @Override
    public boolean isValueSupported(Boolean value) {
        return (value != null);
    }

    @Override
    public boolean isEditable() {
        //TODO improve this when AEModeController is introduced.
        Integer aeMode = cameraController.getCaptureResultValue(CaptureResult.CONTROL_AE_MODE);
        return (aeMode != CaptureResult.CONTROL_AE_MODE_OFF);
    }

    @Override
    public Stream<Boolean> getValues() {
        return Booleans.values().stream();
    }

    @Override
    public Boolean getValue() {
        return cameraController.getCaptureResultValue(CaptureResult.CONTROL_AE_LOCK);
    }

    @Override
    public void setValue(Boolean value) {
        cameraController.setCaptureRequestValue(CaptureRequest.CONTROL_AE_LOCK, value);
    }

    @Override
    public Boolean parseValue(String str) {
        return Boolean.parseBoolean(str);
    }

    @Override
    public CameraSetting getControlledSetting() {
        return CameraSetting.AE_LOCK;
    }

    @Override
    public String getDisplayValue() {
        Boolean value = getValue();
        return (value) ? "ON" : "OFF";
    }
}
