package cn.original.ide.launch.code.file.edit.left;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import java.io.File;
import java.io.IOException;

import cn.original.ide.R;
import cn.original.ide.launch.code.editor.DeveloperCodeEditor;
import cn.original.ide.tree.list.node.TreeNode;

public class ChildViewHolder extends cn.original.ide.tree.list.holder.ChildViewHolder {

    private Activity activity;
    private TextView textView;
    private ImageView imageView;
    private Handler handler;

    void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ChildViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.editor_textView_left_mine_xm_list_child_title);
        imageView = itemView.findViewById(R.id.editor_imageView_left_mine_xm_list_child_logo);
        setHand(imageView);
        initRootView(itemView.findViewById(R.id.editor_linearLayout_left_mine_xm_list_child));
    }

    @Override
    public void onBind(TreeNode node, int point) {
        String string = node.getText();
        textView.setText(string);
        imageView.setImageDrawable(getDrawable(node));
    }

    private Drawable getDrawable(TreeNode node) {
        // System.out.println(node.getPath());
        String string = node.getText();
        if (string.endsWith(".java")) {
            return activity.getDrawable(R.drawable.ic_drawable_java_24dp);
        } else if (string.endsWith(".json")) {
            return activity.getDrawable(R.drawable.ic_drawable_json_24dp);
        } else if (string.endsWith(".css")) {
            return activity.getDrawable(R.drawable.ic_drawable_css_24dp);
        } else if (string.endsWith(".html")) {
            return activity.getDrawable(R.drawable.ic_drawable_html_24dp);
        } else if (string.endsWith(".js")) {
            return activity.getDrawable(R.drawable.ic_drawable_javascript_24dp);
        } else if (string.endsWith(".xml")) {
            return activity.getDrawable(R.drawable.ic_drawable_xml_24dp);
        } else if (string.endsWith(".gradle")) {
            return activity.getDrawable(R.drawable.ic_drawable_gradle_24dp);
        } else if (string.endsWith(".png") || string.endsWith(".jpg")) {
            try {
                Drawable drawable = getImageDrawable(node.getPath());
                if (drawable == null) {
                    drawable = activity.getDrawable(R.drawable.ic_drawable_file_24dp);
                }
                return drawable;
            } catch (IOException e) {
                e.printStackTrace();
                return activity.getDrawable(R.drawable.ic_drawable_file_24dp);
            }
        }
        return activity.getDrawable(R.drawable.ic_drawable_file_24dp);
    }

    public BitmapDrawable getImageDrawable(String path) throws IOException {
        //打开文件
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        BitmapDrawable bd = new BitmapDrawable(activity.getResources(), bitmap);
        return bd;
    }


    void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void openFileInSystem(String path, Context context) {
        if (path.endsWith(".json") || path.endsWith(".css") || path.endsWith(".java") || path.endsWith(".xml") || path.endsWith(".gradle") || path.endsWith(".pro") || path.endsWith(".js") || path.endsWith(".html")) {
            DeveloperCodeEditor developerCodeEditor = activity.findViewById(R.id.editor_codeEditor_view);
            oms.io.File file = new oms.io.File(path);
            developerCodeEditor.setText(file.read());
            DrawerLayout drawerLayout = activity.findViewById(R.id.editor_drawerLayout_topView);
            drawerLayout.closeDrawers();
        }
    }
}
