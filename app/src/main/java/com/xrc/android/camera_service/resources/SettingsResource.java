package com.xrc.android.camera_service.resources;

import com.xrc.android.camera_service.Factory;
import com.xrc.android.camera_service.resources.representation.Setting;
import com.xrc.android.hardware.camera2.settings.CameraSettingsManager;
import com.xrc.restlet.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Objects;

public class SettingsResource extends ServerResource {

    public static final String PATH = "/settings";

    @Get(MediaType.APPLICATION_JSON)
    public Setting[] getSettings() throws ResourceException {
        CameraSettingsManager cameraSettingsManager = Factory.getCameraSettingsManager();
        return cameraSettingsManager.getSupportedSettings()
                .map(Setting::fromCameraSetting)
                .filter(Objects::nonNull)
                .toArray(Setting[]::new);
    }

}

