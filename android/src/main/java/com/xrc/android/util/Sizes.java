package com.xrc.android.util;

import android.util.Size;

public class Sizes {


    public static boolean ratioMatch(Size s1, Size s2, float delta) {
        return Math.abs(ratio(s1) - ratio(s2)) < Math.abs(delta);
    }

    public static float ratio(Size s1) {
        return (float) s1.getWidth() / s1.getHeight();
    }

    public static Size rotate(Size s) {
        return new Size(s.getHeight(), s.getWidth());
    }

    public static Type type(Size s) {
        float r = ratio(s);
        if (r > 1)
            return Type.LANDSCAPE;
        else
            return Type.PORTRAIT;
    }

    public static long area(Size s) {
        return ((long) s.getWidth() * s.getHeight());
    }

    /**
     * Compares sizes by their area.
     */
    public static int compare(Size lhs, Size rhs) {
        return Long.signum(area(lhs) - area(rhs));
    }


    public enum Type {
        PORTRAIT,
        LANDSCAPE,
    }


    /**
     * Compares two {@code Size}s based on their areas.
     */
    public static class Comparator
            implements java.util.Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            return Sizes.compare(lhs, rhs);
        }

    }
}
