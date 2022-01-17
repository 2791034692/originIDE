package oms.ability;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import oms.content.Handler;
import oms.content.Intent;
import oms.content.Message;


public class UIAbility extends AppCompatActivity {

    private boolean _ACTION_BACK_STATE = false;
    private Handler handler;
    protected void onIntent(Intent intent){

    }
    public void setHandler(Handler handler){

    }
    public void postMessage(Message message){

    }


    public void sendMessage(Message message){

    }

    public View InflaterView(int t, ViewGroup group) {
        if (group == null) {
            return getLayoutInflater().inflate(t, null);
        }
        return getLayoutInflater().inflate(t, group, false);
    }

    public void startService(Class<ServiceAbility> serviceAbility){
        Intent intent = new Intent(this,serviceAbility);
        startService(intent);
    }

    public void bindService(Class<ServiceAbility> serviceAbilityClass, ServiceConnection connection,int flags){
        Intent intent = new Intent(this,serviceAbilityClass);
        bindService(intent,connection,flags);
    }

    public void startService(Intent intent){
        super.startService(intent);
    }

    public void bindService(Intent intent, ServiceConnection connection,int flags){
        super.bindService(intent,connection,flags);
    }

    public void startAbility(Intent intent){
        super.startActivity(intent);
    }

    @Override
    public void startActivity(android.content.Intent intent) {

    }

    public void setStatusBarDark(boolean statusBarDark){
        View view = getWindow().getDecorView();
         if(statusBarDark){
             view.setSystemUiVisibility(view.getSystemUiVisibility()|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
         }else{
             view.setSystemUiVisibility(view.getSystemUiVisibility()&(~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
         }
    }

    public int dip2px(float dpValue) {
        float scale = getApplication().getResources().getDisplayMetrics().density;
        return  (int)(dpValue * scale + 0.5f);
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ModAbility.application == null){
            ModAbility.application = getApplicationContext();
        }
        android.content.Intent intent = getIntent();
        Intent intent1 = null;
        if(intent==null){
            intent1 = new Intent();
        }else if(intent instanceof android.content.Intent){
            intent1 = new Intent(intent);
        }else{
            intent1 = (Intent)intent;
        }
        onIntent(intent1);
    }

    public void lan(int t){
        anims(t);
    }
    private final void anims(int t) {
        int i2 ;
        int i3 = R.anim.my_alpha_action;
        switch(t) {
            case 0:
                i2 = R.anim.fade;
                i3 = R.anim.hold;
                break;
            case 1:
                i2 = R.anim.my_scale_action;
                break;
            case 2:
                i2 = R.anim.scale_rotate;
                break;
            case 3:
                i2 = R.anim.scale_translate_rotate;
                break;
            case 4:
                i2 = R.anim.scale_translate;
                break;
            case 5:
                i2 = R.anim.hyperspace_in;
                i3 = R.anim.hyperspace_out;
                break;
            case 6:
                i2 = R.anim.push_left_in;
                i3 = R.anim.push_left_out;
                break;
            case 7:
                i2 = R.anim.push_up_in;
                i3 = R.anim.push_up_out;
                break;
            case 8:
                i2 = R.anim.slide_left;
                i3 = R.anim.slide_right;
                break;
            case 9:
                i2 = R.anim.wave_scale;
                break;
            case 10:
                i2 =  R.anim.zoom_enter;
                i3 = R.anim.zoom_exit;
                break;
            case 11:
                i2 = R.anim.slide_up_in;
                i3 = R.anim.slide_down_out;
                break;
            case 12:
                i2 = R.anim.push_left_in2;
                i3 = R.anim.push_left_out2;
                break;
            default:
                return;
        }

        this.overridePendingTransition(i2, i3);
    }

    public void goAbility(Intent intent){
        startAbility(intent);
    }

    public void end(){
        finish();
    }

    public final <T extends View> T g(int id){
        return findViewById(id);
    }

    @Override
    protected final void onPause() {
        super.onPause();
        if(!_ACTION_BACK_STATE){
            onBack();
            _ACTION_BACK_STATE = true;
        }
    }

    @Override
    protected final void onStop() {
        super.onStop();
        if(!_ACTION_BACK_STATE){
            onBack();
            _ACTION_BACK_STATE = true;
        }
    }

    @Override
    protected final void onResume() {
        super.onResume();
        _ACTION_BACK_STATE = false;
        onRecept();
    }


    public void onRecept(){

    }

    protected void onBack(){

    }

    @Override
    public Intent getIntent() {
        return new Intent(super.getIntent());
    }

    @Override
    protected final void onStart() {
        super.onStart();
    }

    @Override
    protected final void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}

