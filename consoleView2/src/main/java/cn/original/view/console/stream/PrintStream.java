package cn.original.view.console.stream;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileNotFoundException;

import cn.original.view.console.interfaces.ConsoleInterface;

public abstract class PrintStream extends java.io.PrintStream {
    protected final ConsoleInterface consoleInterface;
    protected Handler handler;

    public PrintStream(final ConsoleInterface consoleInterface, File fileName) throws FileNotFoundException {
        super(fileName);
        this.handler = new Handler(Looper.getMainLooper());
        this.consoleInterface = consoleInterface;
    }

    protected abstract void print2(Object o);

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
