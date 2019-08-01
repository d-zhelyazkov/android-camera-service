package com.xrc.android.camera_service.resources.representation;

public class SettingInfo<T> {
    private Setting setting;

    private boolean editable;

    private T value;

    private T[] values;

    public SettingInfo(Setting setting, boolean editable, T value, T[] values) {
        this.setting = setting;
        this.editable = editable;
        this.value = value;
        this.values = values;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T[] getValues() {
        return values;
    }

    public void setValues(T[] values) {
        this.values = values;
    }
}
