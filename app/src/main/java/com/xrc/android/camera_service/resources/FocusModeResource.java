package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.resources.mapper.FocusModeMapper;
import com.xrc.android.camera_service.resources.representation.FocusMode;
import com.xrc.android.camera_service.resources.representation.FocusModeValue;
import com.xrc.android.hardware.camera2.settings.AutoFocusMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;

import java.util.function.IntFunction;

public class FocusModeResource extends EnumSettingResource<FocusMode, AutoFocusMode, FocusModeValue> {

    public static final String PATH = "/settings/FOCUS_MODE";

    public FocusModeResource() {
        super(CameraSetting.AF_MODE, FocusModeMapper.getMapper());
    }

    @Override
    protected IntFunction<FocusMode[]> getArrayGenerator() {
        return FocusMode[]::new;
    }
}
