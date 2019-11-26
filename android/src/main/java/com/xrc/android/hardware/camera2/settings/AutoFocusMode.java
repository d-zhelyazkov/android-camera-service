package com.xrc.android.hardware.camera2.settings;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;

public enum AutoFocusMode implements EnumValue {

    OFF(
            CameraCharacteristics.CONTROL_AF_MODE_OFF,
            CaptureRequest.CONTROL_AF_MODE_OFF,
            CaptureResult.CONTROL_AF_MODE_OFF),

    AUTO(
            CameraCharacteristics.CONTROL_AF_MODE_AUTO,
            CaptureRequest.CONTROL_AF_MODE_AUTO,
            CaptureResult.CONTROL_AF_MODE_AUTO),

    MACRO(
            CameraCharacteristics.CONTROL_AF_MODE_MACRO,
            CaptureRequest.CONTROL_AF_MODE_MACRO,
            CaptureResult.CONTROL_AF_MODE_MACRO),

    CONTINUOUS_PICTURE("CONTINUOUS",
            CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_PICTURE,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE,
            CaptureResult.CONTROL_AF_MODE_CONTINUOUS_PICTURE),

    CONTINUOUS_VIDEO("CONTINUOUS",
            CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_VIDEO,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO,
            CaptureResult.CONTROL_AF_MODE_CONTINUOUS_VIDEO),

    EDOF(
            CameraCharacteristics.CONTROL_AF_MODE_EDOF,
            CaptureRequest.CONTROL_AF_MODE_EDOF,
            CaptureResult.CONTROL_AF_MODE_EDOF);

    private final String displayName;

    private final int characteristicValue;

    private final int captureRequestValue;

    private final int captureResultValue;


    AutoFocusMode(String displayName, int characteristicValue, int captureRequestValue, int captureResultValue) {
        this.displayName = displayName;
        this.characteristicValue = characteristicValue;
        this.captureRequestValue = captureRequestValue;
        this.captureResultValue = captureResultValue;
    }

    AutoFocusMode(int characteristicValue, int captureRequestValue, int captureResultValue) {
        this.displayName = name();
        this.characteristicValue = characteristicValue;
        this.captureRequestValue = captureRequestValue;
        this.captureResultValue = captureResultValue;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int getCharacteristicValue() {
        return characteristicValue;
    }

    @Override
    public int getCaptureRequestValue() {
        return captureRequestValue;
    }

    @Override
    public int getCaptureResultValue() {
        return captureResultValue;
    }

}
