package cn.original.ide.module.content;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import cn.original.ide.module.ability.CompilerAbility;
import cn.original.ide.module.ability.ProjectAbility;
import dalvik.system.DexClassLoader;

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
        if (context instanceof Activity) {
            this.context = context;
        }
    }

    public CompilerAbility getCompilerAbility(String string) {
        return null;
    }

    public ProjectAbility getProjectAbility(String string) {
        return null;
    }

    public Context getCurrentContext() {
        return context;
    }

    public Application initApplication(Application application) {
        application.setManage(this);
        return application;
    }

    public Application initApplication(DexClassLoader dexClassLoader, String className) {
        try {
            Object applicationObj = dexClassLoader.loadClass(className).newInstance();
            if (applicationObj instanceof Application) {
                Application application = (Application) applicationObj;
                application.setManage(this);
                return application;
            }
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
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
