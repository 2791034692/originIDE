package oms.ability;

import android.app.Application;
import android.content.Context;

public class ModAbility {
    static Context application = null;
    public Context getContext(){
        return application;
    }
}
