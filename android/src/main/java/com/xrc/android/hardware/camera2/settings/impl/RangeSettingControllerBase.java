package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Range;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;

public abstract class RangeSettingControllerBase<T extends Comparable<T>> implements CameraSettingController<T> {

    protected final CameraController cameraController;
    protected final CaptureResult.Key<T> resultKey;
    protected final CaptureRequest.Key<T> requestKey;
    protected final CameraCharacteristics.Key<Range<T>> rangeKey;
    protected final CameraSetting cameraSetting;

    public RangeSettingControllerBase(
            CameraController cameraController,
            CaptureResult.Key<T> resultKey,
            CaptureRequest.Key<T> requestKey,
            CameraCharacteristics.Key<Range<T>> rangeKey,
            CameraSetting cameraSetting) {
        this.cameraController = cameraController;
        this.resultKey = resultKey;
        this.requestKey = requestKey;
        this.rangeKey = rangeKey;
        this.cameraSetting = cameraSetting;
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

    @Override
    public T getValue() {
        return cameraController.getCaptureResultValue(resultKey);
    }

    @Override
    public void setValue(T value) {
        cameraController.setCaptureRequestValue(requestKey, value);
    }


    @Override
    public CameraSetting getControlledSetting() {
        return this.cameraSetting;
    }


}
