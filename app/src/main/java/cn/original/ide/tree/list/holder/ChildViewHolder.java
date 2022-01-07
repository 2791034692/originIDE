package cn.original.ide.tree.list.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import cn.original.ide.tree.list.node.TreeNode;


public abstract class ChildViewHolder extends TreeViewHolder {

    public ChildViewHolder(View itemView) {
        super(itemView);
    }


    public void bindView(final TreeNode itemData, int position) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) hand.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth() + offsetMargin;
        hand.setLayoutParams(params);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileInSystem(itemData.getPath(), view.getContext());
            }
        });
        onBind(itemData, position);
    }

    public abstract void onBind(TreeNode node, int point);

    public abstract void openFileInSystem(String path, Context context);

    @SuppressLint("DefaultLocale")
    protected String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf("."));
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }

}
