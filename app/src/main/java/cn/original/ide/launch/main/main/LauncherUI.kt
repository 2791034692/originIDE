package cn.original.ide.launch.main.main

import android.widget.Toast
import androidx.annotation.Nullable
import cn.original.ide.launch.code.LauncherUIEditor
import cn.original.ide.tree.view.ActivityMainTree
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import oms.ability.视窗能力
import oms.content.Intent
import oms.content.意图


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
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(activity, permissions)
                        } else {
                            toast("获取应用权限失败，应用正常使用受阻，将无法使用！")
                        }
                    }
                })
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.Group.STORAGE)) {
                goAbility(Intent(this, LauncherUIEditor::class.java))
            } else {
                toast("用户没有在权限设置页授予权限")
            }
        }
    }

    fun onCreatePath() {

    }

    private fun toast(s: String) {
        Toast.makeText(this.applicationContext, s, Toast.LENGTH_SHORT).show()
    }


}