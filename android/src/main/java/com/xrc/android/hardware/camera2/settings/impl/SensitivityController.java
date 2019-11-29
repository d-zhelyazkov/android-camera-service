package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Range;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.AutoExposureMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;

import java.util.stream.Stream;

public class SensitivityController implements CameraSettingController<Integer> {

    private final CameraController cameraController;

    public SensitivityController(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    @Override
    public boolean isSettingSupported() {
        Range<Integer> sensitivityRange = getInternalValueRange();
        return (sensitivityRange != null);
    }

    @Override
    public boolean isValueSupported(Integer value) {
        Range<Integer> sensitivityRange = getInternalValueRange();
        return sensitivityRange.contains(value);
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
    public Integer getValue() {
        return cameraController.getCaptureResultValue(CaptureResult.SENSOR_SENSITIVITY);
    }

    @Override
    public void setValue(Integer value) {
        cameraController.setCaptureRequestValue(CaptureRequest.SENSOR_SENSITIVITY, value);
    }

    @Override
    public Integer parseValue(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public CameraSetting getControlledSetting() {
        return CameraSetting.ISO;
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

    private Range<Integer> getInternalValueRange() {
        return cameraController.getCameraCharacteristic(
                CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE
        );
    }
}
