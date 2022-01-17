package cn.original.ide.module.ability;

import android.widget.Toast;

import cn.original.ide.module.content.Application;
import cn.original.ide.module.interfaces.AbilityParent;

public abstract class Ability implements AbilityParent {
    private Application application;
    private SettingAbility settingAbility = null;

    public Ability(Application application) {
        this.application = application;
    }

    public final void toast(final Object object) {
        application.getHandler().post(() -> {
            Toast.makeText(application.getApplicationManage().getContext(), object.toString(), Toast.LENGTH_SHORT).show();
        });
    }


    public Application getApplication() {
        return application;
    }

    public SettingAbility getSetting() {
        return settingAbility;
    }

}
