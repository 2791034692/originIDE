package cn.original.node.bean;

public interface NodeBody {
    NodeBody init(Node node);

    Object exec(String name, Object... v);
}
