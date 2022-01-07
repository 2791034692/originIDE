package oms.view.text;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class UITypeface {
    private static HashMap<String, Typeface> typefaceHashMap = new HashMap<>();
    static Typeface getTypefaceFromAssets(Context context,String string){
        Typeface typeface = typefaceHashMap.get(string);
        if(typeface == null){
            typeface = Typeface.createFromAsset(context.getAssets(),string);
            typefaceHashMap.put(string,typeface);
            return typeface;
        }
        return typeface;
    }
    public final static void clear(){
        typefaceHashMap.clear();
        typefaceHashMap = null;
    }
}
