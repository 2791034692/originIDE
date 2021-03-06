package cn.original.ide.launch.code.file.edit.left;

import android.app.Activity;
import android.view.View;

import cn.original.ide.R;
import cn.original.ide.tree.list.adapter.TreeListAdapter;


public class Adapter extends TreeListAdapter {


    private Activity activity;
    private Handler handler;
    public Adapter(Activity context) {
        super(context);
        this.activity = context;
        this.handler = new Handler() {
            @Override
            public void acceptMessage(Message message) {
                super.acceptMessage(message);
                switch (message.getMessage()) {
                    case 1:
                        message.getHolder().isShow();
                        break;
                }
            }
        };
    }

    @Override
    public int getParentViewId() {
        return R.layout.ability_editor_layout_left_mine_xm_list_parent;
    }

    @Override
    public int getChildViewId() {
        return R.layout.ability_editor_layout_left_mine_xm_list_child;
    }


    @Override
    public ParentViewHolder onCreateParentViewHolder(View view) {
        ParentViewHolder parentViewHolder = new ParentViewHolder(view, this.handler);
        parentViewHolder.setActivity(this.activity);
        return parentViewHolder;
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(View view) {
        ChildViewHolder childViewHolder = new ChildViewHolder(view);
        childViewHolder.setActivity(this.activity);
        return childViewHolder;
    }


}
