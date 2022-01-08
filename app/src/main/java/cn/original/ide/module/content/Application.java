package cn.original.ide.module.content;

import android.os.Handler;

public abstract class Application {
    private AppManage system;
    private boolean state = false;
    public static final boolean SUCCESS = true;
    public static final boolean ERROR = false;

    public AppManage getApplicationManage() {
        return system;
    }

    final void setManage(AppManage system) {
        this.system = system;
    }

    public void onStartInterfaces() {
        onStart();
    }

    protected abstract void onStart();

    public Handler getHandler() {
        return system.handler;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return state;
    }

}
