package cn.original.view.console.stream;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;

import cn.original.view.console.interfaces.ConsoleInterface;

public class PrintStream extends java.io.PrintStream {
    private Handler handler;
    private ConsoleInterface consoleInterface;
    /**
     * Creates a new print stream, without automatic line flushing, with the
     * specified file name.  This convenience constructor creates
     * the necessary intermediate {@link OutputStreamWriter
     * OutputStreamWriter}, which will encode characters using the
     * {@linkplain Charset#defaultCharset() default charset}
     * for this instance of the Java virtual machine.
     *
     * @param fileName The name of the file to use as the destination of this print
     *                 stream.  If the file exists, then it will be truncated to
     *                 zero size; otherwise, a new file will be created.  The output
     *                 will be written to the file and is buffered.
     * @throws FileNotFoundException If the given file object does not denote an existing, writable
     *                               regular file and a new regular file of that name cannot be
     *                               created, or if some other error occurs while opening or
     *                               creating the file
     * @throws SecurityException     If a security manager is present and {@link
     *                               SecurityManager#checkWrite checkWrite(fileName)} denies write
     *                               access to the file
     * @since 1.5
     */
    public PrintStream(final ConsoleInterface consoleInterface, File fileName) throws FileNotFoundException {
        super(fileName);
        this.handler = new Handler(Looper.getMainLooper());
        this.consoleInterface = consoleInterface;
    }


    private void print2(final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                consoleInterface.print(object.toString() + "\n");
                System.gc();
            }
        });
    }

    @Override
    public void close() {
        handler = null;
        super.close();
    }

    @Override
    public void print(Object obj) {
        //super.print(obj);
        this.print2(obj);
    }

    @Override
    public void print(char[] s) {
        this.print2(Arrays.toString(s));
    }

    @Override
    public void print(double d) {
        this.print2(d);
    }

    @Override
    public void print(float f) {
        this.print2(f);
    }

    @Override
    public void print(int i) {
        this.print2(i);
    }

    @Override
    public void print(boolean b) {
        this.print2(b);
    }

    @Override
    public void print(long l) {
        this.print2(l);
    }

    @Override
    public void print(char c) {
        this.print2(c);
    }

    @Override
    public void println(boolean x) {
        this.print(x);
    }

    @Override
    public void println(double x) {
        this.print(x);
    }

    @Override
    public void println(Object x) {
        this.print(x);
    }

    @Override
    public void println() {
        this.print("");
    }

    @Override
    public void println(char[] x) {
        this.print(x);
    }

    @Override
    public void println(float x) {
        this.print(x);
    }

    @Override
    public void println(long x) {
        this.print(x);
    }

    @Override
    public void println(char x) {
        this.print(x);
    }

    @Override
    public void println(String x) {
        this.print(x);
    }

    @Override
    public void println(int x) {
        this.print(x);
    }

    @Override
    public void print(String s) {
        this.print2(s);
    }


}
