package oms.view.list.recycle;

import java.util.ArrayList;
import java.util.List;

public class TreeDataGroup extends TreeData {
    private List<TreeData> treeDataList = new ArrayList<>();

    public void addTreeData(TreeData treeData) {
        treeDataList.add(treeData);
    }

    public TreeData get(int t) {
        return treeDataList.get(t);
    }

    public int getSize() {
        return treeDataList.size();
    }
}
