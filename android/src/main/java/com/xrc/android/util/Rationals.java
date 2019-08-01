package com.xrc.android.util;

import android.util.Rational;

public final class Rationals {

    public static Rational divide(Rational dividend, Rational divisor) {
        return new Rational(
                dividend.getNumerator() * divisor.getDenominator(),
                dividend.getDenominator() * divisor.getNumerator()

        );
    }

    public static Rational multiply(int number, Rational rational) {
        return new Rational(
                number * rational.getNumerator(),
                rational.getDenominator()
        );
    }

}
