package cn.original.ide.launch.code.dialog.project;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import cn.original.ide.R;

public class ProjectAddDialog extends BottomSheetDialog {
    private BottomSheetBehavior<FrameLayout> behavior;

    public ProjectAddDialog(Context context) {
        this(context, R.style.BottomSheetDialog);
    }

    public ProjectAddDialog(@NonNull Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_project_add_application_main);
    }


}
