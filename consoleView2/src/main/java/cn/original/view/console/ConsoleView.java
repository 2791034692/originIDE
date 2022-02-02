package cn.original.view.console;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import cn.original.view.R;
import cn.original.view.console.interfaces.ConsoleInterface;
import cn.original.view.console.stream.ErrorPrintStream;
import cn.original.view.console.stream.NormalPrintStream;

public class ConsoleView extends LinearLayout implements ConsoleInterface, View.OnClickListener {
    private final float[] cornerRadii;
    private PrintStream outOldPrintStream;
    private PrintStream errOldPrintStream;
    private NormalPrintStream outNewPrintStream;
    private ErrorPrintStream errNewPrintStream;
    private int state = 0;
    private EditText editText;
    private Button button1, button2, button3;

    public ConsoleView(Context context) {
        this(context, null);
    }

    public ConsoleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private CharSequence all = "";
    private CharSequence normal = "";

    public void clear() {

    }

    private CharSequence error = "";
    private int 白色 = 0xffffffff;
    private int 黑色 = 0xff000000;


    public ConsoleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cornerRadii = new float[]{dip2px(20), dip2px(20), 0, 0};
        this.outOldPrintStream = System.out;
        this.errOldPrintStream = System.err;
        init();
    }

    @Override
    public String input() {
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        outOldPrintStream = null;
        errOldPrintStream = null;
        clear();
    }

    @Override
    public void print(cn.original.view.console.stream.PrintStream printStream, CharSequence c) {
        String string = (String) c;
        if (printStream instanceof NormalPrintStream) {
            if (state == 0 || state == 1) {
                String[] all = ((String) this.all).split("\n");
                String[] normal = ((String) this.normal).split("\n");
                if (all.length < 100 && normal.length < 50) {
                    this.all = this.all + string;
                    this.normal = this.normal + string;
                } else if (all.length < 100 && normal.length > 50) {
                    this.all = this.all + string;
                    this.normal = string;
                } else if (all.length > 100 && normal.length < 50) {
                    this.all = string;
                    this.normal = this.normal + string;
                } else if (all.length > 100 && normal.length > 50) {
                    this.all = string;
                    this.normal = string;
                }
                this.editText.setText(this.editText.getText() + string);
            } else {
                String[] all = ((String) this.all).split("\n");
                String[] normal = ((String) this.normal).split("\n");
                if (all.length < 100 && normal.length < 50) {
                    this.all = this.all + string;
                    this.normal = this.normal + string;
                } else if (all.length < 100 && normal.length > 50) {
                    this.all = this.all + string;
                    this.normal = string;
                } else if (all.length > 100 && normal.length < 50) {
                    this.all = string;
                    this.normal = this.normal + string;
                } else if (all.length > 100 && normal.length > 50) {
                    this.all = string;
                    this.normal = string;
                }
                return;
            }
        } else if (printStream instanceof ErrorPrintStream) {
            String[] all = ((String) this.all).split("\n");
            String[] error = ((String) this.error).split("\n");
            if (state == 0 || state == 2) {
                if (all.length < 100 && error.length < 50) {
                    this.all = this.all + string;
                    this.error = this.error + string;
                } else if (all.length < 100 && error.length > 50) {
                    this.all = this.all + string;
                    this.error = string;
                } else if (all.length > 100 && error.length < 50) {
                    this.all = string;
                    this.error = this.error + string;
                } else if (all.length > 100 && error.length > 50) {
                    this.all = string;
                    this.error = string;
                }
                this.editText.setText(this.editText.getText() + string);
            } else {
                if (all.length < 100 && error.length < 80) {
                    this.all = this.all + string;
                    this.error = this.error + string;
                } else if (all.length < 100 && error.length > 80) {
                    this.all = this.all + string;
                    this.error = string;
                } else if (all.length > 100 && error.length < 80) {
                    this.all = string;
                    this.error = this.error + string;
                } else if (all.length > 100 && error.length > 80) {
                    this.all = string;
                    this.error = string;
                }
                return;
            }
        } else {
            return;
        }
        show();
        System.gc();
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
        super.setBackground(background);
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

    public void load() {
        System.setOut(this.outNewPrintStream);
        System.setErr(this.errNewPrintStream);
    }


    private void addView(int layout) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.console_view_dependency_layout_platform, this);
    }

    public void end() {
        System.setOut(outOldPrintStream);
        System.setErr(errOldPrintStream);
        dismiss();
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

    private void init() {
        dismiss();
        super.setBackgroundResource(R.drawable.ic_drawable_console);
        this.addView(R.layout.console_view_dependency_layout_platform);
        String string = getContext().getFilesDir() + "/print/out3.txt";
        File file = new File(string);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            this.outNewPrintStream = new NormalPrintStream(this, file);
            this.errNewPrintStream = new ErrorPrintStream(this, file);
            this.editText = findViewById(R.id.console_view_dependency_layout_platform_editText);
            this.editText.setHorizontallyScrolling(true);
            this.button1 = findViewById(R.id.console_view_dependency_layout_platform_1);
            this.button2 = findViewById(R.id.console_view_dependency_layout_platform_2);
            this.button3 = findViewById(R.id.console_view_dependency_layout_platform_3);
            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            load();
            //System.out.println(2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isShow() {
        return getVisibility() == VISIBLE;
    }

    public synchronized final void recycle() {
        System.setOut(outOldPrintStream);
        System.setErr(errOldPrintStream);
        dismiss();
        clear();
        outNewPrintStream.close();
        errNewPrintStream.close();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.console_view_dependency_layout_platform_1) {
            state = 0;
            setColor(button1, 黑色, 白色);
            setColor(button2, 白色, getResources().getColor(R.color.正常, getContext().getTheme()));
            setColor(button3, 白色, getResources().getColor(R.color.错误, getContext().getTheme()));
            this.editText.setText(this.all);
        } else if (id == R.id.console_view_dependency_layout_platform_2) {
            state = 1;
            setColor(button1, 白色, 黑色);
            setColor(button2, getResources().getColor(R.color.正常, getContext().getTheme()), 白色);
            setColor(button3, 白色, getResources().getColor(R.color.错误, getContext().getTheme()));
            this.editText.setText(normal);
        } else if (id == R.id.console_view_dependency_layout_platform_3) {
            setColor(button1, 白色, 黑色);
            setColor(button2, 白色, getResources().getColor(R.color.正常, getContext().getTheme()));
            setColor(button3, getResources().getColor(R.color.错误, getContext().getTheme()), 白色);
            state = 2;
            this.editText.setText(error);
        }
    }

    private final void setColor(Button button, int bc, int tc) {
        button.setBackgroundColor(bc);
        button.setTextColor(tc);
    }
}
