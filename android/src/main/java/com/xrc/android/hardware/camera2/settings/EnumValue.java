package com.xrc.android.hardware.camera2.settings;

public interface EnumValue {
    String name();

    int getCharacteristicValue();

    int getCaptureRequestValue();

    int getCaptureResultValue();

    String getDisplayName();
}
