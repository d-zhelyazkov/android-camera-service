package com.xrc.android.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Size;

public class Display {
    public static Size size() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return new Size(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }
}
