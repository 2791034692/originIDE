package cn.original.ide.built.javac;

import cn.original.ide.module.content.Application;

public class JavaCompilerApp extends Application {
    @Override
    protected String getName() {
        return "JavaCompiler";
    }

    @Override
    protected void onStart() {
        initAbility(new JavaProjectAbility(this));
        initAbility(new JavaCompilerAbility(this));
        initAbility(new JavaCMDAbility(this));
        System.out.println("java控制台已装载！");
    }
}
