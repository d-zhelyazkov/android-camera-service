package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;

import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.AutoFocusMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;

public class AutoFocusModeController extends EnumSettingControllerBase<AutoFocusMode> {


    public AutoFocusModeController(CameraController cameraController) {
        super(
                cameraController,
                CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES,
                CaptureResult.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE,
                CameraSetting.AF_MODE,
                AutoFocusMode.class
        );
    }

    @Override
    public boolean isEditable() {
        //TODO improve this when ModeController
        Integer controlMode = cameraController.getCaptureResultValue(CaptureResult.CONTROL_MODE);

        FocusDistanceController focusDistanceController = new FocusDistanceController(this.cameraController);

        return ((controlMode == CaptureResult.CONTROL_MODE_AUTO)
                && !focusDistanceController.isFixed());
    }

}
