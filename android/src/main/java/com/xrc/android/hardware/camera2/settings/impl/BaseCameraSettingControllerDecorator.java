package com.xrc.android.hardware.camera2.settings.impl;

import com.xrc.android.hardware.camera2.settings.BaseCameraSettingController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;

public class BaseCameraSettingControllerDecorator implements BaseCameraSettingController {

    private final BaseCameraSettingController decoratedController;

    public BaseCameraSettingControllerDecorator(BaseCameraSettingController decoratedController) {
        this.decoratedController = decoratedController;
    }

    @Override
    public boolean isSettingSupported() {
        return decoratedController.isSettingSupported();
    }

    @Override
    public boolean isEditable() {
        return decoratedController.isEditable();
    }

    @Override
    public CameraSetting getControlledSetting() {
        return decoratedController.getControlledSetting();
    }

    @Override
    public String getDisplayValue() {
        return decoratedController.getDisplayValue();
    }
}
