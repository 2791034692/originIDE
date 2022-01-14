package cn.original.view.console.print;

import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import cn.original.view.console.interfaces.ConsoleInterface;

public class PrintStream extends java.io.PrintStream {
    private Handler handler;

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
    public PrintStream(ConsoleInterface consoleInterface, File fileName) throws FileNotFoundException {
        super(fileName);
        this.handler = new Handler();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void print(Object obj) {
        //super.print(obj);
    }

    @Override
    public void print(char[] s) {
        super.print(s);
    }

    @Override
    public void print(double d) {
        super.print(d);
    }

    @Override
    public void print(float f) {
        super.print(f);
    }

    @Override
    public void print(int i) {
        super.print(i);
    }

    @Override
    public void print(boolean b) {
        super.print(b);
    }

    @Override
    public void print(long l) {
        super.print(l);
    }

    @Override
    public void print(char c) {
        super.print(c);
    }

    @Override
    public void print(String s) {
        //super.print(s);
    }


}
