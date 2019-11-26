package com.xrc.android.hardware.camera2.settings.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.EnumValue;
import com.xrc.util.Arrays;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class EnumSettingControllerBase<T extends Enum<T> & EnumValue>
        implements CameraSettingController<T> {

    protected final CameraController cameraController;
    protected final CameraCharacteristics.Key<int[]> valuesKey;
    protected final CaptureResult.Key<Integer> resultKey;
    protected final CaptureRequest.Key<Integer> requestKey;
    protected final CameraSetting cameraSetting;
    protected final Class<T> enumValuesClass;

    protected EnumSettingControllerBase(
            CameraController cameraController,
            CameraCharacteristics.Key<int[]> valuesKey,
            CaptureResult.Key<Integer> resultKey,
            CaptureRequest.Key<Integer> requestKey,
            CameraSetting cameraSetting,
            Class<T> valuesClass
    ) {

        this.cameraController = cameraController;
        this.valuesKey = valuesKey;
        this.resultKey = resultKey;
        this.requestKey = requestKey;
        this.cameraSetting = cameraSetting;
        enumValuesClass = valuesClass;
    }

    @Override
    public boolean isSettingSupported() {

        int[] values = getInternalValues();
        return !Arrays.isEmpty(values);
    }

    @Override
    public boolean isValueSupported(T value) {
        int[] values = getInternalValues();
        int characteristicValue = value.getCharacteristicValue();

        return Arrays.contains(values, characteristicValue);
    }

    @Override
    public Stream<T> getValues() {
        int[] values = getInternalValues();
        return java.util.Arrays.stream(values)
                .mapToObj(value ->
                        findValue(T::getCharacteristicValue, value));
    }

    @Override
    public T getValue() {
        int value = cameraController.getCaptureResultValue(resultKey);
        return findValue(T::getCaptureResultValue, value);
    }

    @Override
    public void setValue(T value) {
        int requestValue = value.getCaptureRequestValue();
        cameraController.setCaptureRequestValue(requestKey, requestValue);
    }

    @Override
    public T parseValue(String str) {

        return Enum.valueOf(enumValuesClass, str);
    }

    @Override
    public CameraSetting getControlledSetting() {
        return cameraSetting;
    }

    @Override
    public String getDisplayValue() {
        T value = getValue();
        return value.getDisplayName();
    }

    protected int[] getInternalValues() {
        return cameraController.getCameraCharacteristic(valuesKey);
    }

    protected <V> T findValue(Function<T, V> getter, V value) {

        return Arrays.find(
                enumValuesClass.getEnumConstants(),
                enumValue -> (Objects.equals(
                        getter.apply(enumValue),
                        value
                ))
        );
    }
}
