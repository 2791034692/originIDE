package cn.original.ide.module.ability;

import android.os.Message;
import android.widget.Toast;
import cn.original.ide.module.content.AppManage;
import cn.original.ide.module.content.Application;

public abstract class Ability {
    private Application application;

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

}
