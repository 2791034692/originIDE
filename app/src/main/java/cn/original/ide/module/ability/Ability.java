package cn.original.ide.module.ability;

import android.widget.Toast;

import cn.original.ide.module.content.Application;

public abstract class Ability {
    private Application application;

    public Ability(Application application) {
        this.application = application;
    }

    public void toast(Object object) {
        Toast.makeText(this.application.system.getContext(), object.toString(), Toast.LENGTH_SHORT).show();
    }

    public Application getApplication() {
        return application;
    }
}
