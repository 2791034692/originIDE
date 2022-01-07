package cn.original.ide.settings

import android.graphics.Paint
import android.view.View
import android.widget.AdapterView
import cn.original.ide.R.id
import cn.original.ide.tree.view.AbilitySettingsLayoutTree
import com.suke.widget.SwitchButton
import oms.ability.视窗能力
import oms.content.意图

class LauncherUISettings : 视窗能力(), SwitchButton.OnCheckedChangeListener, View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var viewTree: AbilitySettingsLayoutTree
    private lateinit var settings: Settings
    override fun 当视窗载入时(intent: 意图?) {
        super.当视窗载入时(intent)
        viewTree = AbilitySettingsLayoutTree(this)
        setContentView(viewTree.from())
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        settings = Settings.getInstance(this)
        viewTree.settings_back_viewGroup.setOnClickListener(this::onClick)
        viewTree.settings_switchButton_super_editor_switch.setOnCheckedChangeListener(this::onCheckedChanged)
        viewTree.settings_switchButton_app_theme_open_api_switch.setOnCheckedChangeListener(this::onCheckedChanged)
        viewTree.settings_switchButton_super_editor_open_api_switch.setOnCheckedChangeListener(this::onCheckedChanged)
        viewTree.settings_spinner_super_editor_mod.onItemSelectedListener = this
        var 高性能编辑框: Boolean = settings.getBoolean("编辑器设置", "高性能编辑框")
        if (高性能编辑框) {
            viewTree.settings_switchButton_super_editor_open_api_switch.visibility = View.VISIBLE
            viewTree.settings_spinner_super_editor_mod.visibility = View.VISIBLE
            viewTree.settings_textView_super_editor_mod.paintFlags =
                viewTree.settings_textView_super_editor_mod.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            //viewTree.settings_textView_super_editor_mod.invalidate()
            viewTree.settings_switchButton_super_editor_open_api_switch.isChecked =
                settings.getBoolean("编辑器设置", "开放接口")
            viewTree.settings_spinner_super_editor_mod.setSelection(settings.getInt("编辑器设置", "模式"))
            viewTree.settings_textView_super_editor_open_api_switch.paintFlags =
                viewTree.settings_textView_super_editor_mod.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        } else {
            viewTree.settings_switchButton_super_editor_open_api_switch.visibility = View.GONE
            viewTree.settings_switchButton_super_editor_open_api_switch.isChecked = false
            viewTree.settings_spinner_super_editor_mod.visibility = View.GONE
            viewTree.settings_spinner_super_editor_mod.setSelection(0)
            viewTree.settings_textView_super_editor_mod.paintFlags =
                viewTree.settings_textView_super_editor_mod.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            viewTree.settings_textView_super_editor_open_api_switch.paintFlags =
                viewTree.settings_textView_super_editor_mod.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        viewTree.settings_switchButton_super_editor_switch.isChecked = 高性能编辑框
    }

    override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
        when (view?.id) {
            id.settings_switchButton_super_editor_switch -> {
                settings.set("编辑器设置", "高性能编辑框", isChecked)
                if (!isChecked) {
                    settings.set("编辑器设置", "开放接口", isChecked)
                    settings.set("编辑器设置", "模式", 0)
                    viewTree.settings_switchButton_super_editor_open_api_switch.visibility =
                        View.GONE
                    viewTree.settings_switchButton_super_editor_open_api_switch.isChecked = false
                    viewTree.settings_spinner_super_editor_mod.visibility = View.GONE
                    viewTree.settings_spinner_super_editor_mod.setSelection(0)
                    viewTree.settings_textView_super_editor_mod.paintFlags =
                        viewTree.settings_textView_super_editor_mod.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    viewTree.settings_textView_super_editor_open_api_switch.paintFlags =
                        viewTree.settings_textView_super_editor_mod.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    //viewTree.settings_textView_super_editor_mod.invalidate()
                    //viewTree.settings_textView_super_editor_open_api_switch.invalidate()
                } else {
                    viewTree.settings_switchButton_super_editor_open_api_switch.visibility =
                        View.VISIBLE
                    viewTree.settings_spinner_super_editor_mod.visibility = View.VISIBLE
                    viewTree.settings_textView_super_editor_mod.paintFlags =
                        viewTree.settings_textView_super_editor_mod.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    //viewTree.settings_textView_super_editor_mod.invalidate()
                    viewTree.settings_textView_super_editor_open_api_switch.paintFlags =
                        viewTree.settings_textView_super_editor_mod.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    //viewTree.settings_textView_super_editor_open_api_switch.invalidate()
                }
            }
            id.settings_switchButton_super_editor_open_api_switch -> {
                settings.set("编辑器设置", "开放接口", isChecked)
            }
            id.settings_switchButton_app_theme_open_api_switch -> {
                if (!isChecked) {
                    viewTree.settings_imageView_app_theme_go.visibility = View.GONE
                    viewTree.settings_textView_app_theme_open_api.paintFlags =
                        viewTree.settings_textView_app_theme_open_api.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    viewTree.settings_imageView_app_theme_go.visibility = View.VISIBLE
                    viewTree.settings_textView_app_theme_open_api.paintFlags =
                        viewTree.settings_textView_app_theme_open_api.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            viewTree.settings_spinner_super_editor_mod.id -> {
                settings.set("编辑器设置", "模式", position)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            viewTree.settings_back.id -> {
                结束视窗();
            }
        }
    }
}