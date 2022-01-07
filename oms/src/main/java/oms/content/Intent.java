package oms.content;
import android.app.Application;
import android.net.Uri;

import java.io.Serializable;

import oms.ability.FragmentAbility;
import oms.ability.ServiceAbility;
import oms.ability.UIAbility;
public class Intent extends android.content.Intent {
    public Intent(android.content.Intent intent){
        super(intent);
    }
    public Intent(){
        super();
    }
    public Intent(UIAbility ability,Class className){
        super(ability,className);
    }
    public Intent(ServiceAbility ability,Class className){
        super(ability,className);
    }
    public Intent(Application application, Class className){
        super(application,className);
    }
    public Intent(FragmentAbility ability, Class className){
        this(ability.getAbility(),className);
    }
    public Intent(String string){
        super(string);
    }
    public Intent(String action, Uri uri) {
        super(action,uri);
    }
    @Override
    public Intent clone() {
        return (Intent) super.clone();
    }
    @Override
    public String toString() {
        return "oms.content.Intent{className:"+getClass().getName()+"}";
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
