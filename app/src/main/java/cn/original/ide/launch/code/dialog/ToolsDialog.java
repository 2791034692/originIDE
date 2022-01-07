package cn.original.ide.launch.code.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class ToolsDialog extends AlertDialog {
    protected ToolsDialog(@NonNull Context context) {
        super(context);
        getWindow().getDecorView().setBackground(new ColorDrawable(Color.parseColor("#00000000")));
    }

    public class Builder extends androidx.appcompat.app.AlertDialog.Builder {
        /**
         * Creates a builder for an alert dialog that uses the default alert
         * dialog theme.
         * <p>
         * The default alert dialog theme is defined by
         * {@link android.R.attr#alertDialogTheme} within the parent
         * {@code context}'s theme.
         *
         * @param context the parent context
         */
        public Builder(@NonNull Context context) {
            super(context);
        }

        @NonNull
        @Override
        public AlertDialog create() {
            return null;
        }
    }

}
