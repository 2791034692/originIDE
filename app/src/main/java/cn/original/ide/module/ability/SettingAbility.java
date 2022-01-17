package cn.original.ide.module.ability;

import cn.original.ide.module.content.Application;
import cn.original.ide.module.interfaces.AbilityParent;

//特殊的Ability
public class SettingAbility extends AbilityUI {
    public SettingAbility(Application application) {
        super(application);
    }

    @Override
    protected void onCreateView() {

    }

    @Override
    public AbilityParent getParent() {
        return null;
    }


}
