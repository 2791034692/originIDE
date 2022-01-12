package cn.original.view.console;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ConsoleView extends RelativeLayout {
    public ConsoleView(@NonNull Context context) {
        this(context, null);
    }

    public ConsoleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConsoleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.ic_drawable_console);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public boolean isShow() {
        return getVisibility() == GONE;
    }

    private final ConsoleEditor createConsoleEditor() {
        return new ConsoleEditor(this);
    }

    private final ConsoleGroup createConsoleGroup() {
        return new ConsoleGroup(this);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void dismiss() {
        setVisibility(GONE);
    }


}
