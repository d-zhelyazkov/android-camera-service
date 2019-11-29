package com.xrc.android.camera_service.resources.mapper;

import com.xrc.android.camera_service.resources.representation.AeMode;
import com.xrc.android.hardware.camera2.settings.AutoExposureMode;

import java.util.HashMap;
import java.util.Map;

public class AeModeMapper {
    private static final ValueMapper<AeMode, AutoExposureMode> mapper;

    static {
        Map<AeMode, AutoExposureMode[]> map = new HashMap<>();
        map.put(AeMode.OFF, new AutoExposureMode[]{AutoExposureMode.OFF});

        map.put(AeMode.ON, new AutoExposureMode[]{
                AutoExposureMode.ON,
                AutoExposureMode.ON_ALWAYS_FLASH,
                AutoExposureMode.ON_AUTO_FLASH,
                AutoExposureMode.ON_AUTO_FLASH_REDEYE,
        });
        mapper = new ValueMapper<>(map);
    }

    public static ValueMapper<AeMode, AutoExposureMode> getMapper() {
        return mapper;
    }
}
