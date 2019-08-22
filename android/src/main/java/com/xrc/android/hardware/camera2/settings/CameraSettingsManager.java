package com.xrc.android.hardware.camera2.settings;

import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.impl.SafeCameraSettingController;
import com.xrc.android.hardware.camera2.settings.impl.StringSettingAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class CameraSettingsManager {

    private final CameraController cameraController;

    public CameraSettingsManager(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    public <T> CameraSettingController<T> getSettingController(CameraSetting<T> setting) {
        return constructSettingController(setting);
    }

    public <T> CameraSettingController<String> getStringSettingController(CameraSetting<T> setting) {
        return new StringSettingAdapter<>(getSettingController(setting));
    }

    public Stream<CameraSetting<?>> getSupportedSettings() {
        return CameraSetting.values().stream()
                .filter(setting -> {
                    BaseCameraSettingController settingController = getInternalSettingController(setting);
                    return settingController.isSettingSupported();
                });
    }

    private <T> CameraSettingController<T> constructSettingController(CameraSetting<T> setting) {
        return new SafeCameraSettingController<>(
                getInternalSettingController(setting));
    }

    private <T> CameraSettingController<T> getInternalSettingController(CameraSetting<T> setting) {

        try {
            Class<? extends CameraSettingController<T>> settingControllerClass =
                    setting.getSettingControllerClass();
            Constructor<? extends CameraSettingController<T>> constructor =
                    settingControllerClass.getConstructor(CameraController.class);
            return constructor.newInstance(cameraController);

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
