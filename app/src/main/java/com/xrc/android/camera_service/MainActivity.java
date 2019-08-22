package com.xrc.android.camera_service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.Observable;

public class MainActivity extends Activity {

    private static final int CAMERA_PERMISSION_REQUEST = 1337;

    private final CameraController cameraController = Factory.getCameraController();

    private final Server server = new Server();

    private CameraSettingsDisplay settingsDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            finish();
            return;
        }

        cameraController.init(this);
        server.init();

        TextView cameraSettingsView = findViewById(R.id.camera_settings);
        settingsDisplay = new CameraSettingsDisplay(cameraSettingsView);

        Factory.getSecondaryThreadHandler().post(() -> {
            try {
                cameraController.startPreview();
                server.start();

                setupCaptureToast();
                settingsDisplay.start();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView serverUrlsView = findViewById(R.id.server_urls);
        ServerUrisDisplay.display(serverUrlsView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Factory.getSecondaryThreadHandler().post(() -> {
            try {
                settingsDisplay.stop();
                server.stop();
                cameraController.stopPreview();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setupCaptureToast() {
        String captureMsg = getString(R.string.capture_msg);
        Toast toast = Toast.makeText(this, captureMsg, Toast.LENGTH_SHORT);

        Observable<byte[]> captureImageObservable = cameraController.getCaptureImageObservable();
        captureImageObservable.subscribe(bytes -> toast.show());
    }

}
