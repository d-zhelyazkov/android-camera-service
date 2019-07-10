package com.xrc.android.camera_service;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final int CAMERA_PERMISSION_REQUEST = 1337;

    private final CameraController cameraController = Factory.getCameraController();

    private final Server server = new Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            return;
        }

        try {
            cameraController.startPreview();
            server.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            cameraController.stopPreview();
            server.stop();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {

        cameraController.init(this);
        server.init();

        TextView serverUrlsView = findViewById(R.id.server_urls);
        ServerUrisDisplay.display(serverUrlsView);
    }

}
