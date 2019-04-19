package com.xrc.android.camera_service;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private final Server server = new Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {

        server.init();
    }

}
