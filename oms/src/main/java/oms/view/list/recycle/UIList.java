package oms.view.list.recycle;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class UIList extends RecyclerView {
    private int layoutId = 0;
    public static final int MULTISTAGE_ALL = 0;
    public static final int MULTISTAGE_EXPAND = 1;
    public static final int MULTISTAGE_DEFAULT = 2;
    private int multiType = 2;
    public UIList(@NonNull  Context context){
        this(context,null);
    }
    public UIList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        /*TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIList);
        int id = typedArray.getResourceId(R.styleable.UIList_listLayout,0);
        this.layoutId = id;*/
    }
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
    }
}
