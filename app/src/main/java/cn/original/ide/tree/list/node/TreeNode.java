package cn.original.ide.tree.list.node;

import java.util.List;

public class TreeNode implements Comparable<TreeNode> {

    public static final int ITEM_TYPE_PARENT = 0;
    public static final int ITEM_TYPE_CHILD = 1;

    private String uuid;

    private int type;// 显示类型
    private String text;
    private String path;// 路径
    private int treeDepth = 0;// 路径的深度

    private List<TreeNode> children;

    private boolean expand;// 是否展开

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public TreeNode(int type, String text, String path, String uuid,
                    int treeDepth, List<TreeNode> children) {
        super();
        this.type = type;
        this.text = text;
        this.uuid = uuid;
        this.path = path;
        this.treeDepth = treeDepth;
        this.children = children;
    }

    public TreeNode() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    @Override
    public int compareTo(TreeNode another) {
        return this.getText().compareTo(another.getText());
    }

    public interface TreeNodeClickListener {

        public void onExpandChildren(TreeNode itemData);

        public void onHideChildren(TreeNode itemData);

    }

}
