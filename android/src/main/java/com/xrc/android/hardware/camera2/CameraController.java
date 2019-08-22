package com.xrc.android.hardware.camera2;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;

public interface CameraController {

    <T> T getCaptureResultValue(CaptureResult.Key<T> resultKey);

    <T> void setCaptureRequestValue(CaptureRequest.Key<T> requestKey, T value);

    <T> T getCameraCharacteristic(CameraCharacteristics.Key<T> characteristicKey);

}
