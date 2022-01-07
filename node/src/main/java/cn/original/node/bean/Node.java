package cn.original.node.bean;

import dalvik.system.DexClassLoader;

public abstract class Node {
    private DexClassLoader dexClassLoader;
    private NodeBody body;

    public static Node getInstance() {
        return null;
    }

    public final void setNodeBody(NodeBody body) {
        this.body = body;
    }

    public NodeBody getBody() {
        return body;
    }
}
