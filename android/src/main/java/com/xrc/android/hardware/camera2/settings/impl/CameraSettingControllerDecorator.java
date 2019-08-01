package com.xrc.android.hardware.camera2.settings.impl;

import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.exceptions.UneditableSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingValueException;

import java.util.stream.Stream;

public class CameraSettingControllerDecorator<T>
        extends BaseCameraSettingControllerDecorator
        implements CameraSettingController<T> {

    private final CameraSettingController<T> decoratedSettingController;

    protected CameraSettingControllerDecorator(CameraSettingController<T> decoratedSettingController) {
        super(decoratedSettingController);

        this.decoratedSettingController = decoratedSettingController;
    }

    @Override
    public boolean isValueSupported(T value) throws UnsupportedSettingException {
        return decoratedSettingController.isValueSupported(value);
    }

    @Override
    public Stream<T> getValues() throws UnsupportedSettingException {
        return decoratedSettingController.getValues();
    }

    @Override
    public T getValue() throws UnsupportedSettingException {
        return decoratedSettingController.getValue();
    }

    @Override
    public void setValue(T value)
            throws UnsupportedSettingException, UnsupportedSettingValueException, UneditableSettingException {
        decoratedSettingController.setValue(value);
    }

    @Override
    public T parseValue(String str) {
        return decoratedSettingController.parseValue(str);
    }
}
