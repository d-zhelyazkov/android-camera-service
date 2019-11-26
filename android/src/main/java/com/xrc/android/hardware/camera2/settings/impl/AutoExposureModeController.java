package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.AutoExposureMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;

public class AutoExposureModeController extends EnumSettingControllerBase<AutoExposureMode> {

    public AutoExposureModeController(CameraController cameraController) {
        super(
                cameraController,
                CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES,
                CaptureResult.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE,
                CameraSetting.AE_MODE,
                AutoExposureMode.class
        );
    }

    @Override
    public boolean isEditable() throws UnsupportedSettingException {
        //TODO improve this when ModeController is introduced
        Integer controlMode = cameraController.getCaptureResultValue(CaptureResult.CONTROL_MODE);
        return (controlMode == CaptureResult.CONTROL_MODE_AUTO);
    }
}
