package cn.original.ide.module.ability;

import cn.original.ide.module.content.Application;
import cn.original.ide.module.interfaces.AbilityParent;

public abstract class AbilityUI extends Ability {
    public AbilityUI(Application application) {
        super(application);
    }

    protected abstract void onCreateView();

    @Override
    public AbilityParent getParent() {
        return null;
    }

    public final void onCreateViewInterface() {

    }

}
