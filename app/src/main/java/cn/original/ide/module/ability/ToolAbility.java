package cn.original.ide.module.ability;

import cn.original.ide.module.content.Application;
import cn.original.ide.module.interfaces.AbilityParent;

public class ToolAbility extends Ability {
    public ToolAbility(Application application) {
        super(application);
    }

    @Override
    public AbilityParent getParent() {
        return null;
    }
}
