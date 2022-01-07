package cn.original.node.devel.editor;

import cn.original.node.bean.Node;

public class EditNode extends Node {
    public static EditNode getInstance() {
        return new EditNode();
    }

    public void setText(String text) {
        getBody().exec("setText", text);
    }

    public String getText() {
        return (String) getBody().exec("getText");
    }

    public void openFile(String path) {
        getBody().exec("openFile", path);
    }
}
