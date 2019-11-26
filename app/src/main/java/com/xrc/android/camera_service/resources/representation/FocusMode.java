package com.xrc.android.camera_service.resources.representation;

import com.xrc.android.hardware.camera2.settings.AutoFocusMode;
import com.xrc.util.Arrays;

import java.util.Set;

public enum FocusMode {
    AUTO(AutoFocusMode.AUTO),
    MACRO(AutoFocusMode.MACRO),
    CONTINUOUS(AutoFocusMode.CONTINUOUS_PICTURE, AutoFocusMode.CONTINUOUS_VIDEO),
    EDF(AutoFocusMode.EDOF);

    public static FocusMode mapFocusMode(AutoFocusMode autoFocusMode) {

        return Arrays.find(
                FocusMode.values(),
                mode -> (mode.getMappedAFModes().contains(autoFocusMode)));
    }

    private final Set<AutoFocusMode> mappedAFModes;

    FocusMode(AutoFocusMode... mappedAFModes) {
        this.mappedAFModes = Arrays.asSet(mappedAFModes);
    }

    public Set<AutoFocusMode> getMappedAFModes() {
        return mappedAFModes;
    }


}
