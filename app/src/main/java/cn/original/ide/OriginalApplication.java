package cn.original.ide;

import android.app.Application;

import cn.original.ide.built.d8.D8Application;
import cn.original.ide.built.javac.JavaCompilerApp;
import cn.original.ide.module.System;

public class OriginalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.with(this).init(new JavaCompilerApp());
        System.with(this).init(new D8Application());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
