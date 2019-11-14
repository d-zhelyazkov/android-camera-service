package com.xrc.android.camera_service.resources.representation;

import com.xrc.android.hardware.camera2.settings.FocusMode;
import com.xrc.util.Arrays;

import java.util.Set;

public enum FocusModeRepresentation {
    AUTO(FocusMode.AUTO),
    MACRO(FocusMode.MACRO),
    CONTINUOUS(FocusMode.CONTINUOUS_PICTURE, FocusMode.CONTINUOUS_VIDEO),
    EDF(FocusMode.EDOF);

    public static FocusModeRepresentation mapFocusMode(FocusMode focusMode) {

        return Arrays.find(
                FocusModeRepresentation.values(),
                mode -> (mode.getMappedFocusModes().contains(focusMode)));
    }

    private final Set<FocusMode> mappedFocusModes;

    FocusModeRepresentation(FocusMode... mappedFocusModes) {
        this.mappedFocusModes = Arrays.asSet(mappedFocusModes);
    }

    public Set<FocusMode> getMappedFocusModes() {
        return mappedFocusModes;
    }


}
