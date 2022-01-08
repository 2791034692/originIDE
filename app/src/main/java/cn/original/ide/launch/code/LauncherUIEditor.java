package cn.original.ide.launch.code;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import cn.original.ide.R;
import cn.original.ide.launch.code.file.edit.left.Adapter;
import cn.original.ide.module.System;
import cn.original.ide.settings.Settings;
import cn.original.ide.tree.list.listener.OnScrollToListener;
import cn.original.ide.tree.list.node.TreeNode;
import cn.original.ide.tree.view.AbilityEditorLayoutTree;
import oms.ability.视窗能力;
import oms.content.意图;


public class LauncherUIEditor extends 视窗能力 implements View.OnClickListener {
    private AbilityEditorLayoutTree viewTree;
    //为了使layoutHelper可以使用所有的View能力，因此提前在editor界面进行classloader的注册
    //private ArrayMap<String,ClassLoaderRegistry> stringClassLoaderRegistryArrayMap = new ArrayMap<>();
    private LinearLayoutManager linearLayoutManager;


    void initSymbol() {
        int width = dip2px(40);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if (textView.getText() == "←") {
                    int start = viewTree.editor_codeEditor_view.getSelectionStart();
                    if (start != 0) {
                        start -= 1;
                    }
                    viewTree.editor_codeEditor_view.setSelection(start);
                    return;
                } else if (textView.getText() == "→") {
                    int start = viewTree.editor_codeEditor_view.getSelectionStart();
                    if (start != viewTree.editor_codeEditor_view.getText().length()) {
                        start += 1;
                    }
                    viewTree.editor_codeEditor_view.setSelection(start);
                    return;
                }
                viewTree.editor_codeEditor_view.insert(viewTree.editor_codeEditor_view.getSelectionStart(), textView.getText().toString());
            }
        };
        String[] strings = {"←", "→", "(", ")", ",", "<", ">", "/", "=", "\"", ":", "{", "}", "!", "?", "'"};
        for (String string : strings) {
            TextView view = new TextView(this);
            viewTree.editor_linearLayout_symbolBar_group.addView(view);
            view.setText(string);
            view.setTextColor(Color.parseColor("#000000"));
            view.setGravity(Gravity.CENTER);
            view.setOnClickListener(clickListener);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.height = -1;
            layoutParams.width = width;
            view.setLayoutParams(layoutParams);
        }
    }


    @Override
    protected void 当视窗不可见时() {
        super.当视窗不可见时();
        //通知虚拟机回收内存
        System.gc();
    }

    @Override
    protected void 当视窗销毁时() {
        super.当视窗销毁时();
        //通知虚拟机回收内存y
        System.gc();
        Settings.clear();
    }

    @Override
    protected void 当视窗重载时() {
        super.当视窗重载时();
    }

    @Override
    protected void 当视窗载入时(意图 intent) {
        super.当视窗载入时(intent);
        viewTree = new AbilityEditorLayoutTree(this);
        setContentView(viewTree.from());
        setStatusBarDark(true);
        viewTree.editor_imageView_menu.setOnClickListener(this::onClick);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "app/typeface/comicShanns.otf");
        viewTree.editor_codeEditor_view.setTypeface(typeface);
        viewTree.editor_imageView_redo.setOnClickListener(this::onClick);
        viewTree.editor_imageView_undo.setOnClickListener(this::onClick);
        linearLayoutManager = new LinearLayoutManager(this);
        Adapter adapter = new Adapter(this);
        List<TreeNode> list = adapter.getChildrenByPath("/sdcard/", 0);
        viewTree.recyclerView.setAdapter(adapter);
        viewTree.recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setOnScrollToListener((new OnScrollToListener() {
            @Override
            public void scrollTo(int position) {
                viewTree.recyclerView.scrollToPosition(position);
            }
        }));
        adapter.addAll(list, 0);
        viewTree.editor_codeEditor_view.setKeywordColor(Color.parseColor("#0033B3"));
        initSymbol();
        viewTree.editor_left_linearLayout_xm_tools.setOnClickListener(this::onClick);
    }

    private AlertDialog.Builder toolsDialogBuilder = null;
    private AlertDialog toolsDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editor_imageView_menu:
                viewTree.editor_drawerLayout_topView.openDrawer(Gravity.LEFT);
                break;
            case R.id.editor_imageView_redo:
                viewTree.editor_codeEditor_view.redo();
                break;
            case R.id.editor_imageView_undo:
                viewTree.editor_codeEditor_view.undo();
            case R.id.editor_left_linearLayout_xm_tools:
                if (toolsDialogBuilder == null) {
                    toolsDialogBuilder = new AlertDialog.Builder(this);
                }
                toolsDialogBuilder.setView(R.layout.editor_left_xm_tools_layout);
                toolsDialog = toolsDialogBuilder.create();
                toolsDialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.parseColor("#00000000")));
                toolsDialog.getWindow().getDecorView().setPadding(dip2px(50), dip2px(0), dip2px(50), dip2px(0));
                toolsDialog.show();
                toolsDialog.findViewById(R.id.editor_left_linearLayout_xm_tools_add).setOnClickListener(this::onClick);
                toolsDialog.findViewById(R.id.editor_left_linearLayout_xm_tools_choice).setOnClickListener(this::onClick);
                break;
            case R.id.editor_left_linearLayout_xm_tools_choice:
                viewTree.editor_drawerLayout_topView.closeDrawers();
                toolsDialog.dismiss();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewTree.editor_drawerLayout_topView.isDrawerOpen(Gravity.LEFT) || viewTree.editor_drawerLayout_topView.isDrawerOpen(Gravity.RIGHT)) {
                viewTree.editor_drawerLayout_topView.closeDrawers();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.with(this).logout();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.with(this).logout();
    }
}
