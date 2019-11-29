package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.resources.mapper.AeModeMapper;
import com.xrc.android.camera_service.resources.representation.AeMode;
import com.xrc.android.camera_service.resources.representation.AeModeValue;
import com.xrc.android.hardware.camera2.settings.AutoExposureMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;

import java.util.function.IntFunction;

public class AeModeResource extends EnumSettingResource<AeMode, AutoExposureMode, AeModeValue> {

    public static final String PATH = "/settings/AE_MODE";

    public AeModeResource() {
        super(CameraSetting.AE_MODE, AeModeMapper.getMapper());
    }

    @Override
    protected IntFunction<AeMode[]> getArrayGenerator() {
        return AeMode[]::new;
    }
}
