package cn.original.ide.module.ability;

import cn.original.ide.module.content.Application;
import cn.original.ide.module.interfaces.AbilityParent;

public abstract class CMDAbility extends Ability {

    public CMDAbility(Application application) {
        super(application);
    }

    @Override
    public AbilityParent getParent() {
        return null;
    }

    public abstract void main(String[] strings);

    public abstract String getName();
}
