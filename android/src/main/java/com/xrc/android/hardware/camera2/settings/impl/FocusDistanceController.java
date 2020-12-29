package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Range;

import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.AutoFocusMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;

import java.util.Locale;

/**
 * Controller for camera's focus distance.
 * Distance values in diopters (D).
 */
public class FocusDistanceController extends RangeSettingControllerBase<Float> {

    public FocusDistanceController(CameraController cameraController) {
        super(
                cameraController,
                CaptureResult.LENS_FOCUS_DISTANCE,
                CaptureRequest.LENS_FOCUS_DISTANCE,
                null,
                CameraSetting.FOCUS_DISTANCE
        );
    }

    @Override
    public boolean isEditable() throws UnsupportedSettingException {
        if (this.isFixed())
            return false;

        AutoFocusModeController afController = new AutoFocusModeController(this.cameraController);
        return afController.getValue() == AutoFocusMode.OFF;
    }

    @Override
    protected Range<Float> getInternalValueRange() {
        return new Range<>(
                0.0f,
                this.getMinValue()
        );
    }

    public boolean isFixed() {
        return (this.getMinValue() == 0.0f);
    }

    @Override
    public String getDisplayValue() throws UnsupportedSettingException {
        return String.format(Locale.ENGLISH, "%.3f D", this.getValue());
    }

    @Override
    public Float parseValue(String str) {
        return Float.parseFloat(str);
    }

    @Override
    public boolean isSettingSupported() {
        return (super.isSettingSupported()
                && this.getValue() != null);
    }

    public Float getMinValue() {
        return this.cameraController.getCameraCharacteristic(
                CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
    }


}
