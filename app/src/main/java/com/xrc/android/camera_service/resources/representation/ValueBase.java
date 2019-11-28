package com.xrc.android.camera_service.resources.representation;

public abstract class ValueBase<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SettingValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
