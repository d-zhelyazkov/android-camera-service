package com.xrc.android.camera_service;

import android.os.Handler;
import android.widget.TextView;
import com.xrc.android.hardware.camera2.settings.CameraSetting;
import com.xrc.android.hardware.camera2.settings.CameraSettingController;
import com.xrc.android.hardware.camera2.settings.CameraSettingsManager;
import com.xrc.android.os.Handlers;

import java.time.Duration;
import java.util.stream.Stream;

public class CameraSettingsDisplay {

    private final TextView cameraSettingsView;

    private final Handler handler = Factory.getSecondaryThreadHandler();

    private final Runnable updateTask = () -> {
        updateView();
        handler.postDelayed(this.updateTask, Duration.ofSeconds(1).toMillis());
    };

    public CameraSettingsDisplay(TextView cameraSettingsView) {
        this.cameraSettingsView = cameraSettingsView;
    }

    public void start() {
        handler.post(updateTask);
    }

    public void stop() {
        handler.removeCallbacks(updateTask);
    }

    private void updateView() {
        StringBuilder settingsInfo = new StringBuilder();
        CameraSettingsManager cameraSettingsManager = Factory.getCameraSettingsManager();
        Stream<CameraSetting<?>> supportedSettings = cameraSettingsManager.getSupportedSettings();
        supportedSettings.forEach(cameraSetting -> {
            CameraSettingController<?> settingController = cameraSettingsManager.getSettingController(cameraSetting);
            String value = settingController.getDisplayValue();
            settingsInfo.append(String.format("\n%s: %s", cameraSetting.name(), value));
        });

        Handlers.getMainThreadHandler().post(() ->
                cameraSettingsView.setText(settingsInfo));

    }
}
