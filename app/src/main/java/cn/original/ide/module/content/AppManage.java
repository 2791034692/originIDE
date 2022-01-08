package cn.original.ide.module.content;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public abstract class AppManage {
    private Context context;
    Handler handler;
    public AppManage() {
        this.handler = new Handler();
    }

    public Context getContext() {
        return this.context.getApplicationContext();
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    private class AppHandler extends Handler {
        public AppHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }

}
