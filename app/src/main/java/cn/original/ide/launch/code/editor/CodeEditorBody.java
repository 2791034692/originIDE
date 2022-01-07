package cn.original.ide.launch.code.editor;

import com.myopicmobile.textwarrior.base.BaseLanguage;

import cn.original.node.bean.Node;
import cn.original.node.bean.NodeBody;

public class CodeEditorBody implements NodeBody {
    DeveloperCodeEditor v;

    CodeEditorBody(DeveloperCodeEditor developerCodeEditor) {
        this.v = developerCodeEditor;
    }

    @Override
    public NodeBody init(Node node) {
        return null;
    }

    public DeveloperCodeEditor getV() {
        return this.v;
    }

    @Override
    public Object exec(String name, Object... v) {
        switch (name) {
            case "setText":
                getV().setText(v[0].toString());
                return null;
            case "getText":
                return getV().getText().toString();
            case "setLanguage":
                if (!(v[0] instanceof BaseLanguage)) {
                    return null;
                }
                getV().setLanguage((BaseLanguage) v[0]);
                return null;
        }
        return null;
    }
}
