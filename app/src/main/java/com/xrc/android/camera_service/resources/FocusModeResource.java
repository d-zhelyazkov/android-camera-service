package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.resources.representation.FocusModeRepresentation;
import com.xrc.android.camera_service.resources.representation.FocusModeValue;
import com.xrc.android.camera_service.resources.representation.Setting;
import com.xrc.android.camera_service.resources.representation.SettingInfo;
import com.xrc.android.camera_service.resources.util.CameraResourceUtil;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.FocusMode;
import com.xrc.android.hardware.camera2.settings.exceptions.UneditableSettingException;
import com.xrc.android.hardware.camera2.settings.exceptions.UnsupportedSettingValueException;
import com.xrc.restlet.MediaType;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Objects;
import java.util.Set;

public class FocusModeResource extends ServerResource {

    public static final String PATH = "/settings/FOCUS_MODE";

    @Get(MediaType.APPLICATION_JSON)
    SettingInfo<FocusModeRepresentation> getSettingInfo() throws ResourceException {
        CameraSettingController<com.xrc.android.hardware.camera2.settings.FocusMode> settingController =
                CameraResourceUtil.getSettingController(CameraSetting.FOCUS_MODE);
        return new SettingInfo<>(
                Setting.fromCameraSetting(settingController.getControlledSetting()),
                settingController.isEditable(),
                FocusModeRepresentation.mapFocusMode(settingController.getValue()),
                settingController.getValues()
                        .map(FocusModeRepresentation::mapFocusMode)
                        .filter(Objects::nonNull)
                        .distinct()
                        .toArray(FocusModeRepresentation[]::new));
    }

    @Put(MediaType.APPLICATION_JSON)
    void updateSettingValue(FocusModeValue focusModeValue) throws ResourceException {
        try {
            CameraSettingController<com.xrc.android.hardware.camera2.settings.FocusMode> settingController =
                    CameraResourceUtil.getSettingController(CameraSetting.FOCUS_MODE);
            FocusModeRepresentation focusModeRepresentation = focusModeValue.getValue();
            Set<FocusMode> mappedFocusModes = focusModeRepresentation.getMappedFocusModes();
            FocusMode focusMode = mappedFocusModes.iterator().next();
            settingController.setValue(focusMode);
        } catch (UnsupportedSettingValueException e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid value provided.");
        } catch (UneditableSettingException e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "The value of the setting cannot be edited.");
        }
    }

}
