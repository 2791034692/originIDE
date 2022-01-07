package cn.original.ide.tree.view;

import cn.original.ide.R.layout;
import oms.ability.UIAbility;
import oms.tree.ViewTree;

public class AbilityAboutLayoutTree extends ViewTree {
    public AbilityAboutLayoutTree(UIAbility ability) {
        super(ability);
    }

    @Override
    public int getLayoutID() {
        return layout.ability_about_layout;
    }

    @Override
    public void init() {

    }
}
