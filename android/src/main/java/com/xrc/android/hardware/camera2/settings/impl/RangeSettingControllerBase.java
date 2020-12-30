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

    @Override
    public T getValue() {
        //// FIXME: 30/12/20
        // this method has the same implementation as the base one
        // but for some reason throws the following exception if not overridden...
        // No virtual method getValue()Ljava/lang/Comparable; in class Lcom/xrc/android/hardware/camera2/settings/impl/SensitivityController; or its super classes (declaration of 'com.xrc.android.hardware.camera2.settings.impl.SensitivityController' appears in /data/app/com.xrc.android.camera_service-MEYPTpV3AluaNqIY54Iqxg==/base.apk!classes4.dex)
        return cameraController.getCaptureResultValue(resultKey);
    }

}
