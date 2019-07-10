package com.xrc.android.os;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class Handlers {
    public static Handler getMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

    public static Handler getNewThreadHandler(String threadName) {
        HandlerThread handlerThread = new HandlerThread(threadName);
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }
}
