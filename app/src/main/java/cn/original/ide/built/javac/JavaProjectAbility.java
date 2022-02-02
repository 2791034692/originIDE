package cn.original.ide.built.javac;

import android.graphics.drawable.Drawable;

import cn.original.ide.R;
import cn.original.ide.module.ability.ProjectAbility;
import cn.original.ide.module.content.Application;
import oms.io.File;
import oms.io.Image;
import oms.io.JSON;

public class JavaProjectAbility extends ProjectAbility {
    public JavaProjectAbility(Application application) {
        super(application);
    }

    @Override
    protected AddItem onCreateAddItem() {
        AddItem addItem = new AddItem() {
            @Override
            public Drawable getIcon() {
                return getApplication().getApplicationManage().getContext().getDrawable(R.drawable.ic_drawable_java_24dp);
            }
            @Override
            public String getName() {
                return "JAVA控制台";
            }
        };
        return addItem;
    }

    @Override
    protected void onCreateStart(String string) {
        super.onCreateStart(string);
    }

    @Override
    protected void onCreateProject(Data data) {
        super.onCreateProject(data);
        File file = new File(getProjectPath(data) + "build.json");
        JSON json = new JSON();
        json.put("name", data.getAppName());
        json.put("package", data.getAppPackage());
        JSON json1 = new JSON();
        json1.put("main", data.getAppPackage().replace(".", "/") + "/Main.java");
        JSON json2 = new JSON();
        json2.put("bootclasspath", "$java1.7.jar$");
        json2.put("folder", "app/libs");
        json1.put("libs", json2);
        json.put("config", json1);
        file.write(json.toString());
        new File(getProjectPath(data) + "src/java/" + data.getAppPackage().replace(".", "/") + "/Main.java").write(initMain(data));
        String s = getProjectPath(data) + "src/icon.png";
        getApplicationTable(data.getAppName()).put("iconPath", s);
        new Image().save(s, getItem().getIcon());
    }

    private String initMain(Data data) {
        StringBuilder builder = new StringBuilder();
        builder.append("package ");
        builder.append(data.getAppPackage());
        builder.append(";");
        builder.append("\n");
        builder.append("public class Main{");
        builder.append("\n");
        builder.append("    public static void main(String[] args){\n");
        builder.append("        System.out.println();\n");
        builder.append("    }\n");
        builder.append("}");
        return builder.toString();
    }
}
