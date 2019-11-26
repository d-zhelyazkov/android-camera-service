package com.xrc.android.hardware.camera2.settings;

public abstract class EnumValueBase implements EnumValue {

    private final String displayName;

    private final int characteristicValue;

    private final int captureRequestValue;

    private final int captureResultValue;


    EnumValueBase(
            String displayName, int characteristicValue, int captureRequestValue, int captureResultValue) {

        this.displayName = displayName;
        this.characteristicValue = characteristicValue;
        this.captureRequestValue = captureRequestValue;
        this.captureResultValue = captureResultValue;
    }

    EnumValueBase(int characteristicValue, int captureRequestValue, int captureResultValue) {
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
