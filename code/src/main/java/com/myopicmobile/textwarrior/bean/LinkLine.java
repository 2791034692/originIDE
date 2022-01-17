package com.myopicmobile.textwarrior.bean;

public class LinkLine {

    public static final int TYPE_STRING = 0;

    public static final int TYPE_COMMENT = 1;

    public int type = 0;

    public int line;

    public int startColumn;

    public int endColumn;

    public LinkLine(int type, int line, int startColumn, int endColumn) {
        this.type = type;
        this.line = line;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

}
