package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Pair;
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

    @Override
    public void setValue(AutoExposureMode value) {
        if (value == AutoExposureMode.OFF) {
            turnOffAutoExposure();
        } else {
            super.setValue(value);
        }
    }

    private void turnOffAutoExposure() {
        SensitivityController sensitivityController = new SensitivityController(cameraController);
        Integer sensitivity = sensitivityController.getValue();

        Long exposureTime = cameraController.getCaptureResultValue(CaptureResult.SENSOR_EXPOSURE_TIME);
        Long frameDuration = cameraController.getCaptureResultValue(CaptureResult.SENSOR_FRAME_DURATION);

        cameraController.setCaptureRequestValues(new Pair[]{
                new Pair<>(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF),
                new Pair<>(CaptureRequest.SENSOR_SENSITIVITY, sensitivityController.getValidValue(sensitivity)),
                new Pair<>(CaptureRequest.SENSOR_EXPOSURE_TIME, exposureTime),
                new Pair<>(CaptureRequest.SENSOR_FRAME_DURATION, frameDuration),

        });
    }
}
