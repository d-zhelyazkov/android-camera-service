package com.xrc.android.camera_service.resources.beans;

public class Setting {

    private String name;

    private String value;

    private boolean editable;

    private String[] availableValues;

    public Setting() {
    }

    public Setting(String name, String value, boolean editable, String[] availableValues) {
        this.name = name;
        this.value = value;
        this.editable = editable;
        this.availableValues = availableValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String[] getAvailableValues() {
        return availableValues;
    }

    public void setAvailableValues(String[] availableValues) {
        this.availableValues = availableValues;
    }
}
