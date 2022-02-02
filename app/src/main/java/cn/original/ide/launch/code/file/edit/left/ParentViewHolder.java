package cn.original.ide.launch.code.file.edit.left;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.original.ide.R;
import cn.original.ide.tree.list.node.TreeNode;

public class ParentViewHolder extends cn.original.ide.tree.list.holder.ParentViewHolder {


    private Activity activity;
    private TextView textView;
    private ImageView imageView;
    private Handler handler;

    public ParentViewHolder(@NonNull View itemView, Handler handler) {
        super(itemView);
        setExpand(itemView.findViewById(R.id.editor_imageView_left_mine_xm_list_parent_expand));
        initRootView(itemView.findViewById(R.id.editor_linearLayout_left_mine_xm_list_parent));
        this.handler = handler;
        textView = itemView.findViewById(R.id.editor_textView_left_mine_xm_list_parent_title);
        imageView = itemView.findViewById(R.id.editor_textView_left_mine_xm_list_parent_add);
    }

    public void isShow() {
        rootView.performClick();
    }

    @Override
    public void onBind(TreeNode node, int point) {
        textView.setText(node.getText());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(getPoint());
                Handler.Message message = new Handler.Message() {
                    @Override
                    public TreeNode getNode() {
                        return node;
                    }

                    @Override
                    public int getMessage() {
                        return 1;
                    }

                    @Override
                    public ParentViewHolder getHolder() {
                        return ParentViewHolder.this;
                    }
                };
                handler.sendMessage(message);
            }
        });
    }

    void setHandler(Handler handler) {
        this.handler = handler;
    }

    void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

}
