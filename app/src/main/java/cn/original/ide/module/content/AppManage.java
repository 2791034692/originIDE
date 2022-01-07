package cn.original.ide.module.content;

import android.content.Context;

public abstract class AppManage {
    private Context context;

    public AppManage() {

    }

    public Context getContext() {
        return this.context.getApplicationContext();
    }

    protected void setContext(Context context) {
        this.context = context;
    }
}
