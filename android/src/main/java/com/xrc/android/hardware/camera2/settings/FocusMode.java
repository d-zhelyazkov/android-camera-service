package com.xrc.android.hardware.camera2.settings;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import com.xrc.util.Arrays;

public enum FocusMode {

    MANUAL(CameraCharacteristics.CONTROL_AF_MODE_OFF, CaptureRequest.CONTROL_AF_MODE_OFF,
            CaptureResult.CONTROL_AF_MODE_OFF),
    AUTO(CameraCharacteristics.CONTROL_AF_MODE_AUTO, CaptureRequest.CONTROL_AF_MODE_AUTO,
            CaptureResult.CONTROL_AF_MODE_AUTO),
    MACRO(CameraCharacteristics.CONTROL_AF_MODE_MACRO, CaptureRequest.CONTROL_AF_MODE_MACRO,
            CaptureResult.CONTROL_AF_MODE_MACRO),
    CONTINUOUS_PICTURE(CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_PICTURE,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE, CaptureResult.CONTROL_AF_MODE_CONTINUOUS_PICTURE,
            "CONTINUOUS"),
    CONTINUOUS_VIDEO(CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_VIDEO,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO, CaptureResult.CONTROL_AF_MODE_CONTINUOUS_VIDEO,
            "CONTINUOUS"),
    EDOF(CameraCharacteristics.CONTROL_AF_MODE_EDOF, CaptureRequest.CONTROL_AF_MODE_EDOF,
            CaptureResult.CONTROL_AF_MODE_EDOF);

    public static FocusMode fromCharacteristicValue(int characteristicValue) {
        return Arrays.find(
                FocusMode.values(),
                focusMode -> (focusMode.characteristicValue == characteristicValue));
    }

    public static FocusMode fromCaptureRequestValue(int requestValue) {
        return Arrays.find(
                FocusMode.values(),
                focusMode -> (focusMode.captureRequestValue == requestValue));
    }

    public static FocusMode fromCaptureResultValue(int captureResultValue) {
        return Arrays.find(
                FocusMode.values(),
                focusMode -> (focusMode.captureResultValue == captureResultValue));
    }


    private final int characteristicValue;

    private final int captureRequestValue;

    private final int captureResultValue;

    private final String displayName;

    FocusMode(int characteristicValue, int captureRequestValue, int captureResultValue) {
        this.characteristicValue = characteristicValue;
        this.captureRequestValue = captureRequestValue;
        this.captureResultValue = captureResultValue;

        this.displayName = this.name();
    }

    FocusMode(int characteristicValue, int captureRequestValue, int captureResultValue, String displayName) {
        this.characteristicValue = characteristicValue;
        this.captureRequestValue = captureRequestValue;
        this.captureResultValue = captureResultValue;
        this.displayName = displayName;
    }

    public int getCharacteristicValue() {
        return characteristicValue;
    }

    public int getCaptureRequestValue() {
        return captureRequestValue;
    }

    public String getDisplayName() {
        return displayName;
    }
}
