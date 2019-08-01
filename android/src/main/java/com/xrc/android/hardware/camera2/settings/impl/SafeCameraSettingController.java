package com.xrc.android.hardware.camera2.settings.impl;

import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.exceptions.UneditableSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingValueException;

import java.util.stream.Stream;

public class SafeCameraSettingController<T> extends CameraSettingControllerDecorator<T> {

    public SafeCameraSettingController(CameraSettingController<T> decoratedSetting) {
        super(decoratedSetting);
    }

    @Override
    public Stream<T> getValues() throws UnsupportedSettingException {
        checkSupported();

        return super.getValues();
    }

    @Override
    public T getValue() throws UnsupportedSettingException {
        checkSupported();

        return super.getValue();
    }

    @Override
    public void setValue(T value)
            throws UnsupportedSettingException, UnsupportedSettingValueException, UneditableSettingException {

        checkSupported();

        if (!super.isEditable())
            throw new UneditableSettingException();

        if (!super.isValueSupported(value))
            throw new UnsupportedSettingValueException();

        super.setValue(value);
    }

    @Override
    public boolean isValueSupported(T value) throws UnsupportedSettingException {
        checkSupported();

        return super.isValueSupported(value);
    }

    @Override
    public boolean isEditable()  throws UnsupportedSettingException {
        checkSupported();

        return super.isEditable();
    }

    @Override
    public String getDisplayValue()  throws UnsupportedSettingException {
        checkSupported();

        return super.getDisplayValue();
    }

    private void checkSupported() throws UnsupportedSettingException {
        if (!isSettingSupported())
            throw new UnsupportedSettingException();
    }
}
