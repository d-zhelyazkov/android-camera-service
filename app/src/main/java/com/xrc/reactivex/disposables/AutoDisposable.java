package com.xrc.reactivex.disposables;

import io.reactivex.disposables.Disposable;

public class AutoDisposable implements AutoCloseable {
    private final Disposable disposable;

    public AutoDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
