package cn.original.view.console;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cn.original.consoleview2.R;
import cn.original.view.console.interfaces.ConsoleInterface;

public class ConsoleView extends RelativeLayout implements ConsoleInterface {
    private final float[] cornerRadii;
    private PrintStream outPrintStream;
    private PrintStream errPrintStream;
    private PrintStream printStream;
    private EditText editText;

    public ConsoleView(@NonNull Context context) {
        this(context, null);
    }

    public ConsoleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConsoleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cornerRadii = new float[]{dip2px(20), dip2px(20), 0, 0};
        this.outPrintStream = System.out;
        this.errPrintStream = System.err;
        init();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        outPrintStream = null;
        errPrintStream = null;
        clear();
    }

    public void clear() {

    }

    @Override
    public void print(CharSequence message) {
        this.show();
        this.editText.setText(this.editText.getText() + "/n" + message);
    }

    public void load() {
        System.setErr(this.printStream);
        System.setOut(this.printStream);
    }

    public void end() {
        System.setOut(outPrintStream);
        System.setErr(errPrintStream);
        dismiss();
    }

    @Override
    public void setBackgroundColor(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setStroke(0, color);
        //super.setBackgroundColor(color);
        gradientDrawable.setCornerRadii(cornerRadii);
        super.setBackground(gradientDrawable);
    }

    @Override
    @Deprecated
    public void setBackground(Drawable background) {
        return;
    }

    @Override
    @Deprecated
    public void setBackgroundResource(int resid) {
        return;
    }


    private final int dip2px(float dpValue) {
        float scale = this.getContext().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init() {
        dismiss();
        super.setBackgroundResource(R.drawable.ic_drawable_console);
        this.addView(R.layout.console_view_dependency_layout_platform);
        try {
            this.printStream = new cn.original.view.console.print.PrintStream(new File(getContext().getFilesDir() + "/print/out.db"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.editText = findViewById(R.id.console_view_dependency_layout_platform_editText);
    }


    private void addView(int layout) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.console_view_dependency_layout_platform, this, false);
        this.addView(view);
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


    private <T extends View> T getView(int t) {
        return findViewById(t);
    }

    public synchronized final void recycle() {
        System.setOut(outPrintStream);
        System.setErr(errPrintStream);
        dismiss();
        clear();
        printStream.close();
    }


}
