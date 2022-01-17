package com.myopicmobile.textwarrior.bean;

public class ColorLine {

    public int line;

    public int startColumn;

    public int endColumn;

    public int color;

    public ColorLine(int line, int startColumn, int endColumn, int color) {
        this.line = line;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        this.color = color;
    }
}

