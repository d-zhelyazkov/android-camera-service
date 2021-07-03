package com.xrc.android.hardware.camera2;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Pair;

import java.util.concurrent.atomic.AtomicReference;

public interface CameraController {

    <T> T getCaptureResultValue(CaptureResult.Key<T> resultKey);

    <T> void setCaptureRequestValue(CaptureRequest.Key<T> requestKey, T value);

    void setCaptureRequestValues(Pair<CaptureRequest.Key<Object>, Object>[] values);

    <T> T getCameraCharacteristic(CameraCharacteristics.Key<T> characteristicKey);

    AtomicReference<CaptureResult> getCaptureResult();

}
