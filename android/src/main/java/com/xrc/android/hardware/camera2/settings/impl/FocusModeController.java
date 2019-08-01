package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.FocusMode;
import com.xrc.util.Arrays;

import java.util.stream.Stream;

public class FocusModeController implements CameraSettingController<FocusMode> {

    private final CameraController cameraController;

    public FocusModeController(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    @Override
    public boolean isSettingSupported() {

        int[] availableModes = getModes();
        return !Arrays.isEmpty(availableModes);
    }

    @Override
    public boolean isValueSupported(FocusMode mode) {
        int[] availableModes = getModes();
        int value = mode.getCharacteristicValue();

        return Arrays.contains(availableModes, value);
    }

    @Override
    public boolean isEditable() {
        //TODO improve this when ModeController and FocusDistanceController are introduced
        Integer controlMode = cameraController.getCaptureResultValue(CaptureResult.CONTROL_MODE);
        Float minFocusDistance =
                cameraController.getCameraCharacteristic(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        return ((controlMode == CaptureResult.CONTROL_MODE_AUTO)
                && (minFocusDistance > 0));
    }

    @Override
    public Stream<FocusMode> getValues() {
        int[] availableModes = getModes();
        return java.util.Arrays.stream(availableModes)
                .mapToObj(FocusMode::fromCharacteristicValue);
    }

    @Override
    public FocusMode getValue() {
        int mode = cameraController.getCaptureResultValue(CaptureResult.CONTROL_AF_MODE);
        return FocusMode.fromCaptureResultValue(mode);
    }

    @Override
    public void setValue(FocusMode mode) {
        int requestValue = mode.getCaptureRequestValue();
        cameraController.setCaptureRequestValue(CaptureRequest.CONTROL_AF_MODE, requestValue);
    }

    @Override
    public FocusMode parseValue(String str) {
        return FocusMode.valueOf(str);
    }

    @Override
    public CameraSetting getControlledSetting() {
        return CameraSetting.FOCUS_MODE;
    }

    @Override
    public String getDisplayValue() {
        FocusMode mode = getValue();
        return mode.getDisplayName();
    }

    private int[] getModes() {
        return cameraController.getCameraCharacteristic(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
    }
}
