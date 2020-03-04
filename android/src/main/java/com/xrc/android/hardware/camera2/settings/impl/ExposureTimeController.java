package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Range;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.AutoExposureMode;
import com.xrc.android.hardware.camera2.settings.CameraSetting;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ExposureTimeController extends RangeSettingControllerBase<Long> {
    public ExposureTimeController(CameraController cameraController) {
        super(
                cameraController,
                CaptureResult.SENSOR_EXPOSURE_TIME,
                CaptureRequest.SENSOR_EXPOSURE_TIME,
                CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE,
                CameraSetting.EXPOSURE_TIME);
    }

    @Override
    public Stream<Long> getValues() {
        Range<Long> valueRange = getInternalValueRange();
        return Stream.of(valueRange.getLower(), valueRange.getUpper());
    }

    @Override
    public Long parseValue(String str) {
        return Long.parseLong(str);
    }

    @Override
    public boolean isEditable() {
        AutoExposureModeController aeModeController = new AutoExposureModeController(cameraController);
        AutoExposureMode aeMode = aeModeController.getValue();
        return (aeMode == AutoExposureMode.OFF);
    }

    @Override
    public String getDisplayValue() {
        long valueNs = getValue();
        long oneS = TimeUnit.SECONDS.toNanos(1);
        return String.format(Locale.ENGLISH, "1/%d s", oneS / valueNs);
    }
}
