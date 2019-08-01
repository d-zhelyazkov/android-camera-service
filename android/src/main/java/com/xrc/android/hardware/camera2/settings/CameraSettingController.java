package com.xrc.android.hardware.camera2.settings;

import com.xrc.android.hardware.camera2.settings.exceptions.UneditableSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingValueException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;

import java.util.stream.Stream;

public interface CameraSettingController<T> extends BaseCameraSettingController {
    boolean isValueSupported(T value) throws UnsupportedSettingException;

    Stream<T> getValues() throws UnsupportedSettingException;

    T getValue() throws UnsupportedSettingException;

    void setValue(T value)
            throws UnsupportedSettingException, UnsupportedSettingValueException, UneditableSettingException;

    T parseValue(String str);
}
