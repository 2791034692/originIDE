package cn.original.ide.launch.code;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Process;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.original.ide.R;
import cn.original.ide.launch.code.adapter.PageAdapter;
import cn.original.ide.launch.code.adapter.project.add.ProjectAddItemAdapter;
import cn.original.ide.launch.code.dialog.project.ProjectAddDialog;
import cn.original.ide.launch.code.dialog.project.ProjectItemListView;
import cn.original.ide.launch.code.dialog.project.ProjectListDialog;
import cn.original.ide.launch.code.file.edit.left.Adapter;
import cn.original.ide.module.System;
import cn.original.ide.module.ability.ProjectAbility;
import cn.original.ide.settings.Settings;
import cn.original.ide.tree.list.listener.OnScrollToListener;
import cn.original.ide.tree.list.node.TreeNode;
import cn.original.ide.tree.view.AbilityEditorLayoutTree;
import oms.ability.视窗能力;
import oms.content.意图;
import oms.io.File;
import oms.io.JSON;
import oms.thread.Thread;


public class LauncherUIEditor extends 视窗能力 implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private AbilityEditorLayoutTree viewTree;
    private LinearLayoutManager linearLayoutManager;
    private static String title = "";
    private long exitTime;

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

    private AlertDialog.Builder operationDialogBuilder = null;
    private AlertDialog operationDialog;
    private List<ProjectAbility> projectAbilities = new ArrayList<>();

    private Adapter adapter;
    private ProjectAddItemAdapter addItemAdapter;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private ProjectListDialog c;
    private String file;

    public static String getTitle2() {
        return title;
    }

    @Override
    protected void 当视窗不可见时() {
        super.当视窗不可见时();
        //通知虚拟机回收内存
        System.gc();
        viewTree.editor_consoleView_bind.end();
    }

    @Override
    protected void 当视窗销毁时() {
        super.当视窗销毁时();
        //通知虚拟机回收内存y
        System.gc();
        Settings.clear();
        System.with(this).logout();
        viewTree.editor_consoleView_bind.recycle();
    }

    @Override
    protected void 当视窗重载时() {
        super.当视窗重载时();
        viewTree.editor_consoleView_bind.load();
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
        viewTree.editor_imageView_more.setOnClickListener(this::onClick);
        viewTree.editor_imageView_start.setOnClickListener(this::onClick);
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new Adapter(this);
        viewTree.recyclerView.setAdapter(adapter);
        viewTree.recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setOnScrollToListener((new OnScrollToListener() {
            @Override
            public void scrollTo(int position) {
                viewTree.recyclerView.scrollToPosition(position);
            }
        }));
        viewTree.editor_codeEditor_view.setKeywordColor(Color.parseColor("#0033B3"));
        initSymbol();
        viewTree.editor_left_linearLayout_xm_operation.setOnClickListener(this::onClick);
        ArrayList<View> views = new ArrayList<>();
        PageAdapter adapter = new PageAdapter(views);
        views.add(InflaterView(R.layout.editor_right_tools_layout, null));
        views.add(InflaterView(R.layout.editor_right_console_layout, null));
        viewTree.editor_right_viewPage_top.setAdapter(adapter);
        this.addItemAdapter = new ProjectAddItemAdapter(this, R.layout.dialog_project_add_item);
        addItemAdapter.addOnLoadItem(new oms.view.list.base.Adapter.OnLoadItem<ProjectAbility>() {
            @Override
            public void onLoad(oms.view.list.base.Adapter.ViewHolder holder, int point, ProjectAbility data) {
                holder.setTextViewText(R.id.dialog_project_add_item_text, data.onCreateAddItemInterface().getName());
                holder.setImageViewSrc(R.id.dialog_project_add_item_image, data.onCreateAddItemInterface().getIcon());
                holder.getView(R.id.dialog_project_add_item_viewGroup).setOnClickListener(view -> {
                    c.dismiss();
                    ProjectAddDialog dialog = new ProjectAddDialog(LauncherUIEditor.this);
                    dialog.show();
                    EditText editText1 = dialog.findViewById(R.id.dialog_project_add_application_editText1);
                    EditText editText2 = dialog.findViewById(R.id.dialog_project_add_application_editText2);
                    dialog.findViewById(R.id.dialog_project_add_application_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            String appName = editText1.getText().toString();
                            String appPackage = editText2.getText().toString();
                            String xm = "%原子灵动/项目/" + appName + "/";
                            title = appName;
                            if (new File(xm).isDirectory()) {
                                String text = "名为《" + appName + "》的项目已存在！";
                                Toast.makeText(LauncherUIEditor.this.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                java.lang.System.err.println(text);
                                return;
                            }
                            new File(xm).mkdirs();
                            new File(xm + "app/").mkdirs();
                            new File(xm + "gen/").mkdirs();
                            new File(xm + "application.json").write(new File("@app/application/application.json").read().replace("{name}", appName).replace("{package}", appPackage).replace("{applicationType}", data.onCreateAddItemInterface().getName()));
                            String path = new File(xm).getPath();
                            new Thread() {
                                @Override
                                public void run() {
                                    data.onCreateProjectInterface(new ProjectAbility.Data() {
                                        @Override
                                        public String getAppName() {
                                            return appName;
                                        }

                                        @Override
                                        public String getAppPackage() {
                                            return appPackage;
                                        }
                                    });
                                }
                            };
                            LauncherUIEditor.this.adapter.clear();
                            LauncherUIEditor.this.adapter.notifyDataSetChanged();
                            LauncherUIEditor.this.adapter.addAll(LauncherUIEditor.this.adapter.getChildrenByPath(path, TreeNode.ITEM_TYPE_PARENT), 0);
                            LauncherUIEditor.this.adapter.notifyDataSetChanged();
                            java.lang.System.out.println("项目：" + appName + "创建成功! 当前位于：" + path);
                        }
                    });
                });
            }
        });
        this.adapter.addAll(this.adapter.getChildrenByPath("/sdcard/", 0), TreeNode.ITEM_TYPE_PARENT);
    }

    private void selectProject(String name) {
        List<TreeNode> list = adapter.getChildrenByPath(new File("%原子灵动/项目/").getPath(), 0);
        adapter.addAll(list, 0);
    }

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
                break;
            case R.id.editor_imageView_start:
                if (title == "") {
                    java.lang.System.err.println("请先打开项目！");
                } else {
                    File file1 = new File("%原子灵动/项目/" + title + "/");
                    File file2 = new File(file1.getPath() + "application.json");
                    if (!file2.isFile()) {
                        java.lang.System.err.println("错误：该项目缺少application.json文件！");
                        return;
                    }
                    JSON json = new JSON(file2.read());
                    String modName = null;
                    if (json == null) {
                        java.lang.System.err.println("错误：该项目配置文件application.json发生错误！");
                        return;
                    } else {
                        modName = json.getString("applicationType");
                        if (modName == null) {
                            java.lang.System.err.println("错误：application.json配置文件缺少applicationType！");
                            return;
                        }
                    }
                    ProjectAbility ability = System.with(this).getProjectAbility(modName);
                    if (ability == null) {
                        String s = "该项目无法打开，原因：缺少" + modName + "模组!";
                        java.lang.System.err.println(s);
                    } else {
                        ability.onCreateStartInterface(file1.getPath());
                    }
                }
                break;
            case R.id.editor_left_linearLayout_xm_operation:
                if (operationDialogBuilder == null) {
                    operationDialogBuilder = new AlertDialog.Builder(this);
                }
                operationDialogBuilder.setView(R.layout.editor_left_xm_operation_layout);
                operationDialog = operationDialogBuilder.create();
                operationDialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.parseColor("#00000000")));
                operationDialog.getWindow().getDecorView().setPadding(dip2px(50), dip2px(0), dip2px(50), dip2px(0));
                operationDialog.show();
                operationDialog.findViewById(R.id.editor_left_linearLayout_xm_operation_add).setOnClickListener(this::onClick);
                operationDialog.findViewById(R.id.editor_left_linearLayout_xm_operation_choice).setOnClickListener(this::onClick);
                break;
            case R.id.editor_left_linearLayout_xm_operation_choice:
                viewTree.editor_drawerLayout_topView.closeDrawers();
                operationDialog.dismiss();
                c = new ProjectListDialog(this);
                View v11 = LayoutInflater.from(this).inflate(R.layout.dialog_project_add_item_listview, null, false);
                ProjectItemListView l11 = v11.findViewById(R.id.dialog_project_add_listView);
                c.setContentView(l11);
                break;
            case R.id.editor_left_linearLayout_xm_operation_add:
                projectAbilities = System.with(this).getProjects();
                addItemAdapter.setList(projectAbilities);
                c = new ProjectListDialog(this);
                View v1 = LayoutInflater.from(this).inflate(R.layout.dialog_project_add_item_listview, null, false);
                ProjectItemListView l = v1.findViewById(R.id.dialog_project_add_listView);
                l.setAdapter(addItemAdapter);
                c.setContentView(v1);
                l.bindBottomSheetDialog(v1);
                c.addSpringBackDisLimit(-1);
                c.show();
                operationDialog.dismiss();
                viewTree.editor_drawerLayout_topView.closeDrawers();
                viewTree.editor_consoleView_bind.dismiss();
                java.lang.System.gc();
                break;
            case R.id.editor_imageView_more:
                viewTree.editor_drawerLayout_topView.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewTree.editor_drawerLayout_topView.isDrawerOpen(Gravity.LEFT) || viewTree.editor_drawerLayout_topView.isDrawerOpen(Gravity.RIGHT)) {
                viewTree.editor_drawerLayout_topView.closeDrawers();
                return true;
            } else if (viewTree.editor_consoleView_bind.isShow()) {
                viewTree.editor_consoleView_bind.dismiss();
            } else {
                if ((java.lang.System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
                {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = java.lang.System.currentTimeMillis();
                } else {
                    finish();
                    Process.killProcess(Process.myPid());
                    java.lang.System.exit(0);
                }
                return true;

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.with(this).logout();
        viewTree.editor_consoleView_bind.recycle();
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {

    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
