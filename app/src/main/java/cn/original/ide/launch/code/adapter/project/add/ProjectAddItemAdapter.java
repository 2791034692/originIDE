package cn.original.ide.launch.code.adapter.project.add;

import android.content.Context;

import cn.original.ide.launch.code.dialog.project.ProjectListDialog;
import cn.original.ide.module.ability.ProjectAbility;
import oms.view.list.base.Adapter;

public class ProjectAddItemAdapter extends Adapter<ProjectAbility> {
    private ProjectListDialog dialog;

    public ProjectAddItemAdapter(Context context, int layout) {
        super(context, layout);
    }

    public void setDialog(ProjectListDialog dialog) {
        this.dialog = dialog;
    }

}
