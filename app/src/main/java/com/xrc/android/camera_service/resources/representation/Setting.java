package com.xrc.android.camera_service.resources.representation;

import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.util.Arrays;

public enum Setting {
    ISO(CameraSetting.ISO),
    FOCUS_MODE(CameraSetting.AF_MODE),
    FOCUS_DISTANCE(CameraSetting.FOCUS_DISTANCE),
    AE_MODE(CameraSetting.AE_MODE),
    AE_COMPENSATION(CameraSetting.AE_COMPENSATION),
    AE_LOCK(CameraSetting.AE_LOCK),
    SHUTTER_SPEED(CameraSetting.EXPOSURE_TIME),
    ;

    public static Setting fromCameraSetting(CameraSetting cameraSetting) {
        return Arrays.find(
                Setting.values(),
                setting -> (setting.getCameraSetting() == cameraSetting));
    }

    private final CameraSetting cameraSetting;

    Setting(CameraSetting cameraSetting) {
        this.cameraSetting = cameraSetting;
    }

    public CameraSetting getCameraSetting() {
        return cameraSetting;
    }

}
