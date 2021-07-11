package com.xrc.android.hardware.camera2.settings.impl;

import android.util.Size;

import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.exceptions.UneditableSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;

import java.util.stream.Stream;

public class ResolutionController implements CameraSettingController<Size> {


    private final CameraController cameraController;

    public ResolutionController(CameraController cameraController) {

        this.cameraController = cameraController;
    }

    @Override
    public boolean isSettingSupported() {
        return true;
    }

    @Override
    public boolean isEditable() throws UnsupportedSettingException {
        return false;
    }

    @Override
    public CameraSetting getControlledSetting() throws UnsupportedSettingException {
        return CameraSetting.RES;
    }

    @Override
    public String getDisplayValue() throws UnsupportedSettingException {
        return getValue().toString();
    }

    @Override
    public boolean isValueSupported(Size value) throws UnsupportedSettingException {
        return false;
    }

    @Override
    public Stream<Size> getValues() throws UnsupportedSettingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Size getValue() throws UnsupportedSettingException {
        return cameraController.getResolution();
    }

    @Override
    public void setValue(Size value) throws UneditableSettingException {
        throw new UneditableSettingException();

    }

    @Override
    public Size parseValue(String str) {
        throw new UnsupportedOperationException();
    }
}
