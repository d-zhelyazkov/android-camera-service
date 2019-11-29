package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.resources.mapper.ValueMapper;
import com.xrc.android.camera_service.resources.representation.Setting;
import com.xrc.android.camera_service.resources.representation.SettingInfo;
import com.xrc.android.camera_service.resources.representation.ValueBase;
import com.xrc.android.camera_service.resources.util.CameraResourceUtil;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.exceptions.UneditableSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingValueException;
import com.xrc.restlet.MediaType;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Objects;
import java.util.function.IntFunction;

public abstract class EnumSettingResource<T, V, K extends ValueBase<T>>
        extends ServerResource {

    protected final CameraSetting<V> setting;
    protected final ValueMapper<T, V> valueMapper;

    protected EnumSettingResource(CameraSetting<V> setting, ValueMapper<T, V> valueMapper) {
        this.setting = setting;
        this.valueMapper = valueMapper;
    }

    @Get(MediaType.APPLICATION_JSON)
    public SettingInfo<T> getSettingInfo() throws ResourceException {
        CameraSettingController<V> settingController =
                CameraResourceUtil.getSettingController(setting);
        return new SettingInfo<>(
                Setting.fromCameraSetting(settingController.getControlledSetting()),
                settingController.isEditable(),
                valueMapper.getRepresentationValue(settingController.getValue()),
                settingController.getValues()
                        .map(valueMapper::getRepresentationValue)
                        .filter(Objects::nonNull)
                        .distinct()
                        .toArray(getArrayGenerator()));
    }

    @Put(MediaType.APPLICATION_JSON)
    public void updateSettingValue(K value) throws ResourceException {
        try {
            CameraSettingController<V> settingController =
                    CameraResourceUtil.getSettingController(setting);
            T representationValue = value.getValue();
            V dtoValue = valueMapper.getDto(representationValue);
            settingController.setValue(dtoValue);
        } catch (UnsupportedSettingValueException e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid value provided.");
        } catch (UneditableSettingException e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "The value of the setting cannot be edited.");
        }
    }

    protected abstract IntFunction<T[]> getArrayGenerator();

}
