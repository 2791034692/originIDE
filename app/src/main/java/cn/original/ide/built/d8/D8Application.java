package cn.original.ide.built.d8;

import cn.original.ide.module.content.Application;

public class D8Application extends Application {
    @Override
    protected String getName() {
        return null;
    }

    @Override
    protected void onStart() {
        initAbility(new D8ProjectAbility(this));
        initAbility(new D8CMD(this));
        initAbility(new R8CMD(this));
    }
}
