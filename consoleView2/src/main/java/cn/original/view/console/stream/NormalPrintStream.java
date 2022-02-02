package cn.original.view.console.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;

import cn.original.view.console.interfaces.ConsoleInterface;

public class NormalPrintStream extends PrintStream {
    private String oldString;


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
    public NormalPrintStream(final ConsoleInterface consoleInterface, File fileName) throws FileNotFoundException {
        super(consoleInterface, fileName);
    }

    protected void print2(final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                consoleInterface.print(NormalPrintStream.this, object.toString() + "\n");
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


}
