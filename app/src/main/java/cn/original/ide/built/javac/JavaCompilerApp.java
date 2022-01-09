package cn.original.ide.built.javac;

import cn.original.ide.module.ability.ProjectAbility;
import cn.original.ide.module.content.Application;

public class JavaCompilerApp extends Application {
    @Override
    protected void onStart() {
        initAbility(new JavaProjectAbility(this));
        initAbility(new JavaCompilerAbility(this));
    }
}
