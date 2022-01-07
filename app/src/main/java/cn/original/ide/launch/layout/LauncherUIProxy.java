package cn.original.ide.launch.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LauncherUIProxy extends AppCompatActivity {
    private Resources resources;
    private Context context;

    public LauncherUIProxy(Context context, Resources resources) {
        this.resources = resources;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public Resources getResources() {
        return this.resources;
    }

    @Override
    public Resources.Theme getTheme() {
        return context.getTheme();
    }


}
