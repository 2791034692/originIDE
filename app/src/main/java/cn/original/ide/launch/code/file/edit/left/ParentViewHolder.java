package cn.original.ide.launch.code.file.edit.left;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.original.ide.R;
import cn.original.ide.tree.list.node.TreeNode;

public class ParentViewHolder extends cn.original.ide.tree.list.holder.ParentViewHolder {


    private Activity activity;
    private TextView textView;

    public ParentViewHolder(@NonNull View itemView) {
        super(itemView);
        setExpand(itemView.findViewById(R.id.editor_imageView_left_mine_xm_list_parent_expand));
        initRootView(itemView.findViewById(R.id.editor_linearLayout_left_mine_xm_list_parent));
        textView = itemView.findViewById(R.id.editor_textView_left_mine_xm_list_parent_title);
    }

    @Override
    public void onBind(TreeNode node, int point) {
        textView.setText(node.getText());
    }

    void setActivity(Activity activity) {
        this.activity = activity;
    }
}
