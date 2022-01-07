package oms.ability;

import oms.content.Intent;
import oms.content.意图;
import oms.content.通信;

public class 视窗能力 extends UIAbility {
    public final boolean 是 = true;
    public final boolean 否 = false;
    public final boolean 真 = true;
    public final boolean 假 = false;
    public 意图 取意图(){
        return new 意图(getIntent());
    }
    protected void 当视窗载入时(意图 intent){

    }

    public void 跳转(意图 yitu){
        goAbility(yitu);
    }

    public void 结束视窗(){
        end();
    }

    public void 切换动画(int t){
        lan(t);
    }

    public void 置通信(通信 tongx){

    }

    @Override
    protected final void onIntent(Intent intent) {
        super.onIntent(intent);
        this.当视窗载入时(new 意图(intent));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.当视窗销毁时();
    }

    @Override
    public final  void onRecept() {
        super.onRecept();
        this.当视窗重载时();
    }

    protected void 当视窗重载时() {
    }

    @Override
    protected final void onBack() {
        super.onBack();
        this.当视窗不可见时();
    }

    protected void 当视窗不可见时() {

    }

    protected void 当视窗销毁时() {

    }
}
