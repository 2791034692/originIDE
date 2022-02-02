package cn.original.ide.launch.code.file.edit.left;

import cn.original.ide.tree.list.node.TreeNode;

public class Handler {
    public Handler() {

    }

    public void sendMessage(Message message) {
        this.acceptMessage(message);
    }

    public void acceptMessage(Message message) {

    }

    public interface Message {
        public TreeNode getNode();

        public int getMessage();

        ParentViewHolder getHolder();
    }
}
