package com.xrc.android.hardware.camera2.settings;

import com.xrc.android.hardware.camera2.settings.impl.AutoExposureCompensationController;
import com.xrc.android.hardware.camera2.settings.impl.AutoExposureLockController;
import com.xrc.android.hardware.camera2.settings.impl.AutoFocusModeController;
import com.xrc.android.hardware.camera2.settings.impl.SensitivityController;

import java.util.ArrayList;
import java.util.Collection;

public class CameraSetting<T> {
    private static final Collection<CameraSetting<?>> values = new ArrayList<>();

    public static final CameraSetting<Integer> ISO =
            new CameraSetting<>("ISO", SensitivityController.class);

    public static final CameraSetting<AutoFocusMode> AF_MODE =
            new CameraSetting<>("AF_MODE", AutoFocusModeController.class);

    public static final CameraSetting<Boolean> AE_LOCK =
            new CameraSetting<>("AE_LOCK", AutoExposureLockController.class);

    public static final CameraSetting<Float> AE_COMPENSATION =
            new CameraSetting<>("AE_COMPENSATION", AutoExposureCompensationController.class);


    static Collection<CameraSetting<?>> values() {
        return values;
    }

    private final String name;

    private final Class<? extends CameraSettingController<T>> settingControllerClass;

    private CameraSetting(String name, Class<? extends CameraSettingController<T>> settingControllerClass) {
        this.name = name;
        this.settingControllerClass = settingControllerClass;

        values.add(this);
    }

    Class<? extends CameraSettingController<T>> getSettingControllerClass() {
        return settingControllerClass;
    }

    public String name() {
        return name;
    }
}
