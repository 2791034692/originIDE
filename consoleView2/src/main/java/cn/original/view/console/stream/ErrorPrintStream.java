package cn.original.view.console.stream;

import java.io.File;
import java.io.FileNotFoundException;

import cn.original.view.console.interfaces.ConsoleInterface;

public class ErrorPrintStream extends PrintStream {
    public ErrorPrintStream(ConsoleInterface consoleInterface, File fileName) throws FileNotFoundException {
        super(consoleInterface, fileName);
    }

    @Override
    protected void print2(final Object o) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                consoleInterface.print(ErrorPrintStream.this, o.toString() + "\n");
                System.gc();
            }
        });
    }
}
