package cn.original.ide.launch.main

import android.widget.Toast
import androidx.annotation.Nullable
import cn.original.ide.index.PathIndex
import cn.original.ide.launch.code.LauncherUIEditor
import cn.original.ide.tree.view.ActivityMainTree
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import oms.ability.视窗能力
import oms.content.Intent
import oms.content.意图
import oms.io.File
import oms.thread.Thread


class LauncherUI : 视窗能力() {
    private lateinit var viewTree: ActivityMainTree
    override fun 当视窗载入时(intent: 意图?) {
        super.当视窗载入时(intent)
        viewTree = ActivityMainTree(this)
        setContentView(viewTree.from())
        val activity: LauncherUI = this;
        if (XXPermissions.isGranted(this, Permission.Group.STORAGE)) {
            goAbility(Intent(this, LauncherUIEditor::class.java))
        } else {
            XXPermissions.with(this).permission(Permission.Group.STORAGE)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: List<String>, all: Boolean) {
                        if (all) {
                            goAbility(Intent(activity, LauncherUIEditor::class.java))
                        } else {
                            toast("获取部分权限成功，但部分权限未正常授予")
                        }
                    }

                    override fun onDenied(permissions: List<String>, never: Boolean) {
                        if (never) {
                            toast("被永久拒绝授权，请手动存储权限！")
                            onCreatePath()
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(activity, permissions)
                        } else {
                            toast("获取应用权限失败，应用正常使用受阻，将无法使用！")
                        }
                    }
                })
        }
    }

    fun init() {
        var thread: Thread = object : Thread() {
            override fun run() {

            }
        }
    }

    fun onCreatePath() {
        File(PathIndex.APPLICATION_FOLDER_PATH).mkdirs()
        File(PathIndex.BACKUPS_FOLDER_PATH).mkdirs()
        File(PathIndex.MODULE_FOLDER_PATH).mkdirs()
        File(PathIndex.PROJECT_FOLDER_PATH).mkdirs()
        var hisOpen: File = File(PathIndex.HISTORY_OPEN_FILE_PATH)
        if (!hisOpen.exists() && !hisOpen.isFile) {
            hisOpen.write("")
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.Group.STORAGE)) {
                onCreatePath()
                goAbility(Intent(this, LauncherUIEditor::class.java))
            } else {
                toast("用户没有在权限设置页授予权限")
            }
        }
    }


    private fun toast(s: String) {
        Toast.makeText(this.applicationContext, s, Toast.LENGTH_SHORT).show()
    }


}