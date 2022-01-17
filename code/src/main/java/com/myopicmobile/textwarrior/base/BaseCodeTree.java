package com.myopicmobile.textwarrior.base;

import java.util.ArrayList;
import java.util.List;

public class BaseCodeTree {

    public Node root;
    private Node curr;

    public BaseCodeTree() {
        root = new Node();
        root.startLine = 0;
        root.endLine = Integer.MAX_VALUE;
        root.isBlock = true;
        root.children = new ArrayList<>(256);
        curr = root;
    }

    public void enterCodeBlock(int line) {
        Node sub = new Node();
        sub.startLine = line;
        sub.endLine = 0;
        if (sub.children == null) {
            sub.children = new ArrayList<>(16);
        }
        sub.isBlock = true;
        sub.parent = curr;
        curr.children.add(sub);
        curr = sub;
    }

    public Node exitCodeBlock(int line) {
        curr.endLine = line;
        Node block = curr;
        boolean empty = curr.children.isEmpty();
        curr = curr.parent;
        if (curr == null) {
            curr = root;
        }
        if (empty) {
            curr.children.remove(block);
        }
        return block;
    }

    public void addVariant(int line, String name, String type) {
        Node var = new Node();
        var.startLine = line;
        var.endLine = line;
        var.isBlock = false;
        var.varName = name;
        var.varType = type;
        curr.children.add(var);
    }

    public static class Node {

        public int startLine = 0;
        public int endLine = 0;
        public List<Node> children = null;
        public boolean isBlock = false;
        public String varName = null;
        public String varType = null;
        public Node parent = null;

    }

}
