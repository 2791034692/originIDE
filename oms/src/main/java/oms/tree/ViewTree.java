package oms.tree;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;


public abstract class ViewTree {
    private final Activity ability;
    private ViewGroup group;
    private View view;
    public ViewTree(Activity ability){
        this(ability,false);
    }
    public ViewTree(Activity ability,boolean fal){
        this.ability = ability;
        group = ability.getWindow().getDecorView().findViewById(android.R.id.content);
        if(fal){
            this.ability.setContentView(from());
        }
    }
    public final View from(){
        View view = this.ability.getLayoutInflater().inflate(getLayoutID(),group,false);
        this.view = view;
        init();
        return view;
    }
    public abstract int getLayoutID();

    public abstract void init();

    protected <T extends View> T findViewById(int t){
        return view.findViewById(t);
    }


}
