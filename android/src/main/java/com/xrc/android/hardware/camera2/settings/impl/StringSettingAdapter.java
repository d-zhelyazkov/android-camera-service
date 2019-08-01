package com.xrc.android.hardware.camera2.settings.impl;

import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingValueException;

import java.util.stream.Stream;

public class StringSettingAdapter<T>
        extends BaseCameraSettingControllerDecorator
        implements CameraSettingController<String> {

    private final CameraSettingController<T> adaptedSetting;

    public StringSettingAdapter(CameraSettingController<T> adaptedSetting) {
        super(adaptedSetting);

        this.adaptedSetting = adaptedSetting;
    }

    @Override
    public boolean isValueSupported(String valueStr) throws UnsupportedSettingException {

        T value = adaptedSetting.parseValue(valueStr);
        return adaptedSetting.isValueSupported(value);
    }

    @Override
    public Stream<String> getValues() throws UnsupportedSettingException {
        return adaptedSetting.getValues()
                .map(T::toString);
    }

    @Override
    public String getValue() throws UnsupportedSettingException {
        return adaptedSetting.getValue().toString();
    }

    @Override
    public void setValue(String valueStr)
            throws UnsupportedSettingException, UnsupportedSettingValueException {

        try {
            T value = adaptedSetting.parseValue(valueStr);
            adaptedSetting.setValue(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UnsupportedSettingValueException();
        }
    }

    @Override
    public String parseValue(String str) {
        return str;
    }

}
