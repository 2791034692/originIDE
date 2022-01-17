package cn.original.ide.module.content;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;

import androidx.annotation.NonNull;

import cn.original.ide.module.ability.CMDAbility;
import cn.original.ide.module.ability.CompilerAbility;
import cn.original.ide.module.ability.ProjectAbility;
import cn.original.ide.module.ability.SettingAbility;
import cn.original.ide.module.ability.ToolAbility;
import dalvik.system.DexClassLoader;

public abstract class AppManage {
    private Context context;
    Handler handler;
    String ID;
    private ArrayMap<String, ProjectAbility> projectAbilitiesIndex = new ArrayMap<>();
    private ArrayMap<String, CompilerAbility> compilerAbilitiesIndex = new ArrayMap<>();
    private ArrayMap<String, ToolAbility> toolAbilitiesIndex = new ArrayMap<>();
    private ArrayMap<String, SettingAbility> settingAbilitiesIndex = new ArrayMap<>();
    private ArrayMap<String, CMDAbility> cmdAbilityArrayMap = new ArrayMap<>();

    public AppManage(String string) {
        this.handler = new Handler();
        this.ID = string;
    }

    public ArrayMap<String, CMDAbility> getCmdAbilityArrayMap() {
        return cmdAbilityArrayMap;
    }

    public Context getContext() {
        return this.context.getApplicationContext();
    }

    public ArrayMap<String, CompilerAbility> getCompilerAbilitiesIndex() {
        return compilerAbilitiesIndex;
    }

    public ArrayMap<String, ProjectAbility> getProjectAbilitiesIndex() {
        return projectAbilitiesIndex;
    }


    protected void setContext(Context context) {
        if (context instanceof Activity) {
            this.context = context;
        }
    }

    public CompilerAbility getCompilerAbility(String string) {
        return compilerAbilitiesIndex.get(string);
    }

    public ProjectAbility getProjectAbility(String string) {
        return projectAbilitiesIndex.get(string);
    }

    public ToolAbility getToolAbility(String string) {
        return toolAbilitiesIndex.get(string);
    }

    public SettingAbility getSettingAbility(String string) {
        return settingAbilitiesIndex.get(string);
    }

    public Context getCurrentContext() {
        return context;
    }

    public Application initApplication(Application application) {
        application.setManage(this);
        application.setID(ID);
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
