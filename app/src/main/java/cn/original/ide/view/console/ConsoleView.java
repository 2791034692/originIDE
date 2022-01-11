package cn.original.ide.view.console;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

public class ConsoleView extends LinearLayoutCompat {
    public ConsoleView(@NonNull Context context) {
        this(context, null);
    }

    public ConsoleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConsoleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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


}
