package cn.original.ide;

import android.app.Application;

public class OriginalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
