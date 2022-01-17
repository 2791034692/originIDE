package cn.original.ide.module.ability;

import cn.original.ide.module.content.Application;
import cn.original.ide.module.interfaces.CompilerAbilityParent;

public class CompilerAbility extends Ability implements CompilerAbilityParent {
    private CompilerAbilityParent compilerAbilityParent = null;

    public CompilerAbility(Application application) {
        super(application);
    }

    //传递path
    public final boolean onStartInterface(String[] string) {
        if (compilerAbilityParent != null && compilerAbilityParent instanceof CompilerAbility) {
            return ((CompilerAbility) compilerAbilityParent).onStartInterface(string);
        }
        return onStart(string);
    }

    protected boolean onStart(String[] strings) {
        return false;
    }

    public final void setParent(CompilerAbilityParent compilerAbilityParent) {
        this.compilerAbilityParent = compilerAbilityParent;
    }

    @Override
    public CompilerAbilityParent getParent() {
        return compilerAbilityParent;
    }
}
