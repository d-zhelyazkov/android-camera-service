package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Range;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.AutoExposureMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;

import java.util.stream.Stream;

public class SensitivityController extends RangeSettingControllerBase<Integer> {

    public SensitivityController(CameraController cameraController) {
        super(
                cameraController,
                CaptureResult.SENSOR_SENSITIVITY,
                CaptureRequest.SENSOR_SENSITIVITY,
                CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE,
                CameraSetting.ISO
        );
    }

    @Override
    public boolean isEditable() {
        AutoExposureModeController aeModeController = new AutoExposureModeController(cameraController);
        AutoExposureMode aeMode = aeModeController.getValue();
        return (aeMode == AutoExposureMode.OFF);
    }

    @Override
    public Stream<Integer> getValues() {
        Range<Integer> sensitivityRange = getInternalValueRange();
        return Stream.of(
                sensitivityRange.getLower(),
                getMaxAnalogSensitivity(),
                sensitivityRange.getUpper());
    }

    @Override
    public Integer parseValue(String str) {
        return Integer.parseInt(str);
    }


    @Override
    public String getDisplayValue() {
        int value = getValue();
        return Integer.toString(value);
    }

    private int getMaxAnalogSensitivity() {
        return cameraController.getCameraCharacteristic(
                CameraCharacteristics.SENSOR_MAX_ANALOG_SENSITIVITY
        );
    }
}
