package cn.original.view.console.interfaces;

import cn.original.view.console.stream.PrintStream;

public interface ConsoleInterface {
    void print(PrintStream printStream, CharSequence charSequence);

    String input();

    void show();
}
