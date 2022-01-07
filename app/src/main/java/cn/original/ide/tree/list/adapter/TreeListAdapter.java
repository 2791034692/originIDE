package cn.original.ide.tree.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import cn.original.ide.tree.list.holder.ChildViewHolder;
import cn.original.ide.tree.list.holder.ParentViewHolder;
import cn.original.ide.tree.list.holder.TreeViewHolder;
import cn.original.ide.tree.list.listener.OnScrollToListener;
import cn.original.ide.tree.list.node.TreeNode;

public abstract class TreeListAdapter extends RecyclerView.Adapter<TreeViewHolder> {

    private Context mContext;
    private List<TreeNode> mDataSet;
    private OnScrollToListener onScrollToListener;

    public void setOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.onScrollToListener = onScrollToListener;
    }

    public TreeListAdapter(Context context) {
        mContext = context;
        mDataSet = new ArrayList<TreeNode>();
    }

    @Override
    public final TreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TreeNode.ITEM_TYPE_PARENT:
                view = LayoutInflater.from(mContext).inflate(
                        getParentViewId(), parent, false);
                return onCreateParentViewHolder(view);
            case TreeNode.ITEM_TYPE_CHILD:
                view = LayoutInflater.from(mContext).inflate(
                        getChildViewId(), parent, false);
                return onCreateChildViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(
                        getParentViewId(), parent, false);
                return onCreateChildViewHolder(view);
        }

    }

    public abstract int getParentViewId();

    public abstract int getChildViewId();

    public abstract ParentViewHolder onCreateParentViewHolder(View view);

    public abstract ChildViewHolder onCreateChildViewHolder(View view);


    @Override
    public void onBindViewHolder(TreeViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TreeNode.ITEM_TYPE_PARENT:
                ParentViewHolder imageViewHolder = (ParentViewHolder) holder;
                imageViewHolder.bindView(mDataSet.get(position), position, imageClickListener);
                break;
            case TreeNode.ITEM_TYPE_CHILD:
                ChildViewHolder textViewHolder = (ChildViewHolder) holder;
                textViewHolder.bindView(mDataSet.get(position), position);
                break;
            default:
                break;
        }
    }

    private TreeNode.TreeNodeClickListener imageClickListener = new TreeNode.TreeNodeClickListener() {

        @Override
        public void onExpandChildren(TreeNode itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<TreeNode> children = getChildrenByPath(itemData.getPath(), itemData.getTreeDepth());
            if (children == null) {
                return;
            }
            addAll(children, position + 1);// 插入到点击点的下方
            itemData.setChildren(children);
            if (onScrollToListener != null) {
                onScrollToListener.scrollTo(position + 1);
            }
        }

        @Override
        public void onHideChildren(TreeNode itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<TreeNode> children = itemData.getChildren();
            if (children == null) {
                return;
            }
            removeAll(position + 1, getChildrenCount(itemData) - 1);
            if (onScrollToListener != null) {
                onScrollToListener.scrollTo(position);
            }
            itemData.setChildren(null);
        }
    };

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private int getChildrenCount(TreeNode item) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        printChild(item, list);
        return list.size();
    }

    private void printChild(TreeNode item, List<TreeNode> list) {
        list.add(item);
        if (item.getChildren() != null) {
            for (int i = 0; i < item.getChildren().size(); i++) {
                printChild(item.getChildren().get(i), list);
            }
        }
    }

    /**
     * 根据路径获取子目录或文件
     *
     * @param path
     * @param treeDepth
     * @return
     */
    public List<TreeNode> getChildrenByPath(String path, int treeDepth) {
        treeDepth++;
        try {
            List<TreeNode> list = new ArrayList<TreeNode>();
            File file = new File(path);
            File[] children = file.listFiles();
            List<TreeNode> fileList = new ArrayList<TreeNode>();
            for (File child : children) {
                if (child.isDirectory()) {
                    list.add(new TreeNode(TreeNode.ITEM_TYPE_PARENT, child
                            .getName(), child.getAbsolutePath(), UUID
                            .randomUUID().toString(), treeDepth, null));
                } else {
                    fileList.add(new TreeNode(TreeNode.ITEM_TYPE_CHILD, child
                            .getName(), child.getAbsolutePath(), UUID
                            .randomUUID().toString(), treeDepth, null));
                }
            }
            Collections.sort(list);
            Collections.sort(fileList);
            list.addAll(fileList);
            return list;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 从position开始删除，删除
     *
     * @param position
     * @param itemCount 删除的数目
     */
    protected void removeAll(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            mDataSet.remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < mDataSet.size(); i++) {
            if (uuid.equalsIgnoreCase(mDataSet.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getType();
    }

    public void add(TreeNode text, int position) {
        mDataSet.add(position, text);
        notifyItemInserted(position);
    }

    public void addAll(List<TreeNode> list, int position) {
        mDataSet.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
    }

    public void delete(int pos) {
        if (pos >= 0 && pos < mDataSet.size()) {
            if (mDataSet.get(pos).getType() == TreeNode.ITEM_TYPE_PARENT
                    && mDataSet.get(pos).isExpand()) {// 父组件并且子节点已经展开
                for (int i = 0; i < mDataSet.get(pos).getChildren().size() + 1; i++) {
                    mDataSet.remove(pos);
                }
                notifyItemRangeRemoved(pos, mDataSet.get(pos).getChildren()
                        .size() + 1);
            } else {// 孩子节点，或没有展开的父节点
                mDataSet.remove(pos);
                notifyItemRemoved(pos);
            }
        }
    }

}
