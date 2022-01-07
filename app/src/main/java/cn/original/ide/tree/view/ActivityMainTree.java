package cn.original.ide.tree.view;

import oms.ability.UIAbility;
import oms.tree.ViewTree;

import static cn.original.ide.R.layout;

public class ActivityMainTree extends ViewTree {
    public android.widget.TextView sample_text1;

    public ActivityMainTree(UIAbility ability) {
        super(ability);
    }

    @Override
    public int getLayoutID() {
        return layout.activity_main;
    }

    @Override
    public void init() {

    }
}
