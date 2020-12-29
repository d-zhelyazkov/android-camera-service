package com.xrc.android.hardware.camera2.settings;

import com.xrc.android.hardware.camera2.CameraController;
import com.xrc.android.hardware.camera2.settings.impl.AutoExposureCompensationController;
import com.xrc.android.hardware.camera2.settings.impl.AutoExposureLockController;
import com.xrc.android.hardware.camera2.settings.impl.AutoExposureModeController;
import com.xrc.android.hardware.camera2.settings.impl.AutoFocusModeController;
import com.xrc.android.hardware.camera2.settings.impl.ExposureTimeController;
import com.xrc.android.hardware.camera2.settings.impl.FocusDistanceController;
import com.xrc.android.hardware.camera2.settings.impl.SafeCameraSettingController;
import com.xrc.android.hardware.camera2.settings.impl.SensitivityController;
import com.xrc.android.hardware.camera2.settings.impl.StringSettingAdapter;

import java.util.Arrays;
import java.util.stream.Stream;

public class CameraSettingsManager {

    private final CameraController cameraController;

    public CameraSettingsManager(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    public <T> CameraSettingController<T> getSettingController(CameraSetting setting) {
        return constructSettingController(setting);
    }

    public CameraSettingController<String> getStringSettingController(CameraSetting setting) {
        return new StringSettingAdapter<>(getSettingController(setting));
    }

    public Stream<CameraSetting> getSupportedSettings() {
        return Arrays.stream(CameraSetting.values())
                .filter(setting -> {
                    BaseCameraSettingController settingController = getInternalSettingController(setting);
                    return settingController.isSettingSupported();
                });
    }

    private <T> CameraSettingController<T> constructSettingController(CameraSetting setting) {
        return new SafeCameraSettingController<>(
                getInternalSettingController(setting));
    }

    private <T> CameraSettingController<T> getInternalSettingController(CameraSetting setting) {

        CameraSettingController<?> controller;
        switch (setting) {
            case ISO:
                controller = new SensitivityController(cameraController);
                break;
            case AF_MODE:
                controller = new AutoFocusModeController(cameraController);
                break;
            case FOCUS_DISTANCE:
                controller = new FocusDistanceController(cameraController);
                break;
            case AE_MODE:
                controller = new AutoExposureModeController(cameraController);
                break;
            case AE_LOCK:
                controller = new AutoExposureLockController(cameraController);
                break;
            case AE_COMPENSATION:
                controller = new AutoExposureCompensationController(cameraController);
                break;
            case EXPOSURE_TIME:
                controller = new ExposureTimeController(cameraController);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + setting);
        }

        //noinspection unchecked
        return (CameraSettingController<T>) controller;

    }
}
