package cn.original.view.console.stream;

import java.io.IOException;

import cn.original.view.console.interfaces.ConsoleInterface;

public class InputStream extends java.io.InputStream {
    private ConsoleInterface consoleInterface;

    public InputStream(ConsoleInterface consoleInterface) {
        this.consoleInterface = consoleInterface;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return super.read(b);
    }

}
