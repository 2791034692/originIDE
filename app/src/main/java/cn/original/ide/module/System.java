package cn.original.ide.module;

import android.content.Context;
import android.content.Intent;

import java.io.PrintStream;
import java.util.ArrayList;

import cn.original.ide.module.ability.ProjectAbility;
import cn.original.ide.module.content.AppManage;
import cn.original.ide.module.content.Application;
import oms.ability.UIAbility;
import oms.io.File;
import oms.io.JSON;

public class System extends AppManage {
    private ArrayList<ProjectAbility> projectAbilities = new ArrayList<>();
    private static System mSystem;
    private Context context;
    public static final int a = 0;

    System(Context context) {
        super();
        this.context = context;
    }

    //获取到被注册的ProjectAbility
    public ProjectAbility[] getProjects() {
        return null;
    }

    //插件安装
    public void install(String path) {

    }

    //插件导入，仅先加载需要使用的，生成的类会在AppManage中存储，避免进行再一次反射
    public void init(int point, Class<UIAbility> uiAbilityClass) {
        File file = new File("$app/module/");
        for (String string : file.list()) {
            File jsonFile = new File("$app/module/" + string + "/app.json");
            if (jsonFile.exists()) {
                JSON json = new JSON(file.read());
            }
            //如果不存在app.json文件就跳过
            continue;
        }
        gc();
        //通知回收无用变量
    }

    private void goUIAbility(Class<UIAbility> uiAbilityClass) {
        Intent intent = new Intent(context, uiAbilityClass);
        context.startActivity(intent);
    }

    public static System with(Context context) {
        if (mSystem == null) {
            mSystem = new System(context);
        }
        return mSystem;
    }

    public void logout() {
        mSystem = null;
        gc();
    }

    public void startApplication(Application application) {
        application.onStartInterfaces();
    }


    public static void gc() {
        java.lang.System.gc();
    }

    public static PrintStream out = java.lang.System.out;
    public static PrintStream err = java.lang.System.err;

    public static void setOut(PrintStream printStream) {
        java.lang.System.setOut(printStream);
    }

    public static void setErr(PrintStream printStream) {
        java.lang.System.setErr(printStream);
    }

}
