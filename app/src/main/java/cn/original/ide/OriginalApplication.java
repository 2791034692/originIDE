package cn.original.ide;

import android.app.Application;

import cn.original.ide.module.System;

public class OriginalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.with(this);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
