package cn.original.ide.module.content;

import android.os.Handler;

import cn.original.ide.module.ability.Ability;

public abstract class Application {
    private AppManage system;
    private boolean state = false;
    public static final boolean SUCCESS = true;
    public static final boolean ERROR = false;
    private String ID;

    public final String getOriginalID() {
        return ID;
    }

    public AppManage getApplicationManage() {
        return system;
    }

    void setID(String ID) {
        this.ID = ID;
    }

    public void initAbility(Ability ability) {

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
