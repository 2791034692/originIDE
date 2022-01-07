package cn.original.ide.launch.splash;

import android.widget.LinearLayout;

import androidx.core.splashscreen.SplashScreen;

import cn.original.ide.launch.main.main.LauncherUI;
import oms.ability.视窗能力;
import oms.content.意图;

public class LauncherUISplash extends 视窗能力 {
    @Override
    protected void 当视窗载入时(意图 intent) {
        super.当视窗载入时(intent);
        SplashScreen.installSplashScreen(this);
        setContentView(new LinearLayout(this));
        意图 yitu = new 意图(this, LauncherUI.class);
        跳转(yitu);
        切换动画(0);
        结束视窗();
    }
}
