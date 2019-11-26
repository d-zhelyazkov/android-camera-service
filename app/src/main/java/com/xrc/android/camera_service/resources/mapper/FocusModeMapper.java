package com.xrc.android.camera_service.resources.mapper;

import com.xrc.android.camera_service.resources.representation.FocusMode;
import com.xrc.android.hardware.camera2.settings.AutoFocusMode;

import java.util.HashMap;
import java.util.Map;

public class FocusModeMapper {
    private static final ValueMapper<FocusMode, AutoFocusMode> mapper;

    static {
        Map<FocusMode, AutoFocusMode[]> map = new HashMap<>();
        map.put(FocusMode.AUTO, new AutoFocusMode[]{AutoFocusMode.AUTO});
        map.put(FocusMode.MACRO, new AutoFocusMode[]{AutoFocusMode.MACRO});
        map.put(FocusMode.CONTINUOUS, new AutoFocusMode[]{
                AutoFocusMode.CONTINUOUS_PICTURE, AutoFocusMode.CONTINUOUS_VIDEO
        });
        map.put(FocusMode.EDF, new AutoFocusMode[]{AutoFocusMode.EDOF});

        mapper = new ValueMapper<>(map);
    }

    public static ValueMapper<FocusMode, AutoFocusMode> getMapper() {
        return mapper;
    }
}
