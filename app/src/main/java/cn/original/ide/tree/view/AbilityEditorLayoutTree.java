package cn.original.ide.tree.view;

import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import cn.original.ide.R.id;
import cn.original.ide.R.layout;
import cn.original.ide.launch.code.editor.DeveloperCodeEditor;
import cn.original.view.console.ConsoleView;
import oms.ability.UIAbility;
import oms.tree.ViewTree;


public class AbilityEditorLayoutTree extends ViewTree {
    public DrawerLayout editor_drawerLayout_topView;
    public ImageView editor_imageView_menu;
    public DeveloperCodeEditor editor_codeEditor_view;
    public ImageView editor_imageView_redo;
    public ImageView editor_imageView_undo;
    public RecyclerView recyclerView;
    public LinearLayout editor_linearLayout_symbolBar_group;
    public ImageView editor_imageView_layoutHelp;
    public LinearLayout editor_left_linearLayout_xm_operation;
    public ConsoleView editor_consoleView_bind;
    public ImageView editor_imageView_more;
    public ViewPager editor_right_viewPage_top;

    public AbilityEditorLayoutTree(UIAbility ability) {
        super(ability);
    }

    @Override
    public int getLayoutID() {
        return layout.ability_editor_layout;
    }

    @Override
    public void init() {
        editor_drawerLayout_topView = findViewById(id.editor_drawerLayout_topView);
        editor_imageView_menu = findViewById(id.editor_imageView_menu);
        editor_codeEditor_view = findViewById(id.editor_codeEditor_view);
        editor_imageView_redo = findViewById(id.editor_imageView_redo);
        editor_imageView_undo = findViewById(id.editor_imageView_undo);
        recyclerView = findViewById(id.recyclerView);
        editor_linearLayout_symbolBar_group = findViewById(id.editor_linearLayout_symbolBar_group);
        editor_imageView_layoutHelp = findViewById(id.editor_imageView_layoutHelp);
        editor_left_linearLayout_xm_operation = findViewById(id.editor_left_linearLayout_xm_operation);
        editor_consoleView_bind = findViewById(id.editor_consoleView_bind);
        editor_imageView_more = findViewById(id.editor_imageView_more);
        editor_right_viewPage_top = findViewById(id.editor_right_viewPage_top);
    }
}
