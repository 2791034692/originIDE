package cn.original.ide.launch.about;

import cn.original.ide.tree.view.AbilityAboutLayoutTree;
import oms.ability.视窗能力;
import oms.content.意图;

public class LauncherUIAbout extends 视窗能力 {
    private AbilityAboutLayoutTree viewTree;

    @Override
    protected void 当视窗载入时(意图 intent) {
        super.当视窗载入时(intent);
        viewTree = new AbilityAboutLayoutTree(this);
        setContentView(viewTree.from());
    }


}
