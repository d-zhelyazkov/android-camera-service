package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Range;

import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;

import java.util.stream.Stream;

public abstract class RangeSettingControllerBase<T extends Comparable<T>>
        extends BaseSettingController<T> {

    protected final CameraCharacteristics.Key<Range<T>> rangeKey;

    public RangeSettingControllerBase(
            CameraController cameraController,
            CaptureResult.Key<T> resultKey,
            CaptureRequest.Key<T> requestKey,
            CameraCharacteristics.Key<Range<T>> rangeKey,
            CameraSetting cameraSetting) {
        super(cameraController, resultKey, requestKey, cameraSetting);

        this.rangeKey = rangeKey;
    }

    @Override
    public boolean isSettingSupported() {
        Range<T> valueRange = getInternalValueRange();
        return (valueRange != null);
    }

    @Override
    public boolean isValueSupported(T value) {
        Range<T> valueRange = getInternalValueRange();
        return valueRange.contains(value);
    }

    @Override
    public Stream<T> getValues() {
        Range<T> valueRange = getInternalValueRange();
        return Stream.of(valueRange.getLower(), valueRange.getUpper());
    }

    protected T getValidValue(T value) {
        Range<T> valueRange = getInternalValueRange();

        T lowestValue = valueRange.getLower();
        if (value.compareTo(lowestValue) < 0)
            return lowestValue;

        T highestValue = valueRange.getUpper();
        if (value.compareTo(highestValue) > 0)
            return highestValue;

        return value;
    }

    protected Range<T> getInternalValueRange() {
        return cameraController.getCameraCharacteristic(rangeKey);
    }

}
