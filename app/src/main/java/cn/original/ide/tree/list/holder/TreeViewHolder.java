package cn.original.ide.tree.list.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TreeViewHolder extends RecyclerView.ViewHolder {
    protected ViewGroup rootView;
    protected View hand;
    protected int itemMargin;
    protected int offsetMargin;


    public TreeViewHolder(@NonNull View itemView) {
        super(itemView);
        int px = dip2px(itemView.getContext(), 8);
        this.offsetMargin = px;
        this.itemMargin = px;
    }

    public void initRootView(ViewGroup itemMargin) {
        this.rootView = itemMargin;
    }

    public void setHand(View hand) {
        this.hand = hand;
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
