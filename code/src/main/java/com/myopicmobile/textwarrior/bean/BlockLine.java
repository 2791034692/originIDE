package com.myopicmobile.textwarrior.bean;

public class BlockLine {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FLOW_DOWN = 1;
    public static final int TYPE_FLOW_UP = 2;
    public int startLine;
    public int endLine;
    public int startColumn;
    public int endColumn;
    public int type = TYPE_NORMAL;

    public BlockLine(int startLine, int endLine, int startColumn, int endColumn) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

}
