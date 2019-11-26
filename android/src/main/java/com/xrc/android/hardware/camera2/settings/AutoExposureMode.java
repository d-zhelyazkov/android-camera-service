package com.xrc.android.hardware.camera2.settings;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;

public enum AutoExposureMode implements EnumValue {

    OFF(
            CameraCharacteristics.CONTROL_AE_MODE_OFF,
            CaptureRequest.CONTROL_AE_MODE_OFF,
            CaptureResult.CONTROL_AE_MODE_OFF),

    ON(
            CameraCharacteristics.CONTROL_AE_MODE_ON,
            CaptureRequest.CONTROL_AE_MODE_ON,
            CaptureResult.CONTROL_AE_MODE_ON),

    ON_AUTO_FLASH(
            CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH,
            CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH,
            CaptureResult.CONTROL_AE_MODE_ON_AUTO_FLASH),

    ON_ALWAYS_FLASH(
            CameraCharacteristics.CONTROL_AE_MODE_ON_ALWAYS_FLASH,
            CaptureRequest.CONTROL_AE_MODE_ON_ALWAYS_FLASH,
            CaptureResult.CONTROL_AE_MODE_ON_ALWAYS_FLASH),

    ON_AUTO_FLASH_REDEYE(
            CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE,
            CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE,
            CaptureResult.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE);


    private final String displayName;

    private final int characteristicValue;

    private final int captureRequestValue;

    private final int captureResultValue;


    AutoExposureMode(
            String displayName, int characteristicValue, int captureRequestValue, int captureResultValue) {

        this.displayName = displayName;
        this.characteristicValue = characteristicValue;
        this.captureRequestValue = captureRequestValue;
        this.captureResultValue = captureResultValue;
    }

    AutoExposureMode(int characteristicValue, int captureRequestValue, int captureResultValue) {
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
