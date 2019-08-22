package com.xrc.android.hardware.camera2.settings;

import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingException;

public interface BaseCameraSettingController {

    boolean isSettingSupported();

    boolean isEditable() throws UnsupportedSettingException;

    CameraSetting getControlledSetting() throws UnsupportedSettingException ;

    String getDisplayValue() throws UnsupportedSettingException ;

}
