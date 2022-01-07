package cn.original.ide.tree.list.holder;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;

import java.util.List;

import cn.original.ide.tree.list.node.TreeNode;

public abstract class ParentViewHolder extends TreeViewHolder {
    private View expand;
    public static final int TREE_NODE_EXPAND_LISTENER_SHOW = 0;
    public static final int TREE_NODE_EXPAND_LISTENER_DISMISS = 1;

    public ParentViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bindView(final TreeNode itemData, final int position, final TreeNode.TreeNodeClickListener imageClickListener) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) expand.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth() + offsetMargin;
        expand.setLayoutParams(params);
        if (itemData.isExpand()) {
            expand.setRotation(45);
            List<TreeNode> children = itemData.getChildren();
        } else {
            expand.setRotation(0);
        }
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    if (itemData.isExpand()) {
                        imageClickListener.onHideChildren(itemData);
                        itemData.setExpand(false);
                        rotationExpandIcon(45, 0);
                    } else {
                        imageClickListener.onExpandChildren(itemData);
                        itemData.setExpand(true);
                        rotationExpandIcon(0, 45);
                    }
                }

            }
        });
        onBind(itemData, position);
    }

    public void setExpand(View view) {
        this.expand = view;
    }


    public abstract void onBind(final TreeNode itemData, final int position);


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
            valueAnimator.setDuration(150);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    expand.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }

}
