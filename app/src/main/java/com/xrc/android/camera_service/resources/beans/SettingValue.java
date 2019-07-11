package com.xrc.android.camera_service.resources.beans;

public class SettingValue {
    private String value;

    public SettingValue() {
    }

    public SettingValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SettingValue{" +
                "value='" + value + '\'' +
                '}';
    }
}