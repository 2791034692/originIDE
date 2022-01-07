package oms.content;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;
import oms.ability.FragmentAbility;
import oms.ability.ServiceAbility;
import oms.ability.UIAbility;
import oms.ability.服务能力;
import oms.ability.片段能力;
import oms.ability.视窗能力;

public class 意图 extends Intent{
    public 意图(android.content.Intent intent){
        super(intent);
    }
    public 意图(){
        super();
    }
    public 意图(UIAbility ability, Class className){
        super(ability,className);
    }
    public 意图(ServiceAbility ability, Class className){
        super(ability,className);
    }
    public 意图(FragmentAbility ability, Class className){
        this(ability.getAbility(),className);
    }
    public 意图(String string){
        super(string);
    }
    public 意图(String action, Uri uri) {
        super(action,uri);
    }
    public 意图 添加(String string,String s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,String[] s){
        return (意图)putExtra(string,s);
    }

    public 意图 添加(String string,CharSequence s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,CharSequence[] s){
        return (意图)putExtra(string,s);
    }

    public 意图 添加(String string,int s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,int[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,byte s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,byte[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,char s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,char[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,boolean s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,boolean[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,long s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,long[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,short s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,short[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,float s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,float[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string,Bundle s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string, Parcelable[] s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string, Parcelable s){
        return (意图)putExtra(string,s);
    }
    public 意图 添加(String string, Serializable s){
        return (意图)putExtra(string,s);
    }
    public String 取字符串(String string){
        return getStringExtra(string);
    }
    public CharSequence 取字符序列(String s){
        return getCharSequenceExtra(s);
    }
    public int 取整数(String s){
        return getIntExtra(s,0);
    }
    public long 取长整数(String s){
        return getLongExtra(s,0);
    }
    public short 取短整数(String s){
        short sh = 0;
        return getShortExtra(s,sh);
    }
    public float 取浮点数(String s){
        return getFloatExtra(s,0f);
    }
    public double 取双精度(String s){
        return getDoubleExtra(s,0.0);
    }
    public boolean 取是否(String s){
        return getBooleanExtra(s,false);
    }
    public String[] 取字符串组(String string){
        return getStringArrayExtra(string);
    }
    public CharSequence[] 取字符序列组(String s){
        return getCharSequenceArrayExtra(s);
    }
    public int[] 取整数组(String s){
        return getIntArrayExtra(s);
    }
    public long[] 取长整数组(String s){
        return getLongArrayExtra(s);
    }
    public short[] 取短整数组(String s){
        return getShortArrayExtra(s);
    }
    public float[] 取浮点数组(String s){
        return getFloatArrayExtra(s);
    }
    public double[] 取双精度组(String s){
        return getDoubleArrayExtra(s);
    }
    public boolean[] 取是否组(String s){
        return getBooleanArrayExtra(s);
    }
    public Parcelable 取映射(String s){
        return getParcelableExtra(s);
    }

    public Parcelable[] 取映射组(String s){
        return getParcelableArrayExtra(s);
    }

    public Serializable 取序列(String s){
        return getSerializableExtra(s);
    }
    public void 置行动(String action){
        setAction(action);
    }
    public String 取行动(){
        return getAction();
    }
    public void 置包名(String s){
        setPackage(s);
    }
    public String 取包名(){
        return getPackage();
    }
    public void 置类名(服务能力 s, Class s1){
        this.setClass(s,s1);
    }
    public void 置类名(视窗能力 s, Class s1){
        this.setClass(s,s1);
    }
    public void 置类名(片段能力 s, Class s1){
        this.setClass(s.取视窗(),s1);
    }
    public void 置类名(String s,String s1){
        this.setClassName(s,s1);
    }
    public void 置类名(Context s, String s1){
        this.setClassName(s,s1);
    }





}
