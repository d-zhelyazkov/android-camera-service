package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Range;
import android.util.Rational;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AutoExposureCompensationController implements CameraSettingController<Float> {

    private static final Range<Integer> UNSUPPORTED_SETTING_RANGE = new Range<>(0, 0);

    private final CameraController cameraController;

    public AutoExposureCompensationController(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    @Override
    public boolean isSettingSupported() {
        Range<Integer> internalValuesRange = getRange();
        return !internalValuesRange.equals(UNSUPPORTED_SETTING_RANGE);
    }

    @Override
    public boolean isValueSupported(Float value) {
        return getValues()
                .anyMatch(supportedValue -> (Objects.equals(value, supportedValue)));
    }

    @Override
    public boolean isEditable() {
        //TODO improve this when AEModeController is introduced.
        Integer aeMode = cameraController.getCaptureResultValue(CaptureResult.CONTROL_AE_MODE);
        return (aeMode != CaptureResult.CONTROL_AE_MODE_OFF);
    }

    @Override
    public Stream<Float> getValues() {

        Range<Integer> valuesRange = getRange();
        Integer smallestValue = valuesRange.getLower();
        Integer biggestValue = valuesRange.getUpper();
        return IntStream.rangeClosed(smallestValue, biggestValue)
                .mapToObj(this::convertInternalValue);
    }

    @Override
    public Float getValue() throws UnsupportedOperationException {
        int value = cameraController.getCaptureResultValue(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
        return convertInternalValue(value);
    }

    @Override
    public void setValue(Float value) {

        int internalValue = convertToInternalValue(value);
        cameraController.setCaptureRequestValue(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, internalValue);
    }

    @Override
    public Float parseValue(String str) {
        return Float.parseFloat(str);
    }

    @Override
    public CameraSetting getControlledSetting() {
        return CameraSetting.AE_COMPENSATION;
    }

    @Override
    public String getDisplayValue() {
        float value = getValue();
        return String.format(Locale.ENGLISH, "%.1f", value);
    }

    private Float convertInternalValue(int value) {
        return value * getStep();
    }

    private int convertToInternalValue(Float value) {
        return (int) (value / getStep());
    }

    private float getStep() {
        Rational step = cameraController.getCameraCharacteristic(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP);
        return step.floatValue();
    }

    private Range<Integer> getRange() {
        return cameraController.getCameraCharacteristic(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
    }

}
