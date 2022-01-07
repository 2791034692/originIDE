package cn.original.ide.tree.view;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.suke.widget.SwitchButton;

import cn.original.ide.R.id;
import cn.original.ide.R.layout;
import oms.ability.UIAbility;
import oms.tree.ViewTree;


public class AbilitySettingsLayoutTree extends ViewTree {
    public ImageView settings_back, settings_imageView_app_theme_go;
    public LinearLayout settings_back_viewGroup;
    public SwitchButton settings_switchButton_super_editor_switch, settings_switchButton_super_editor_open_api_switch, settings_switchButton_app_theme_open_api_switch;
    public Spinner settings_spinner_super_editor_mod;
    public TextView settings_textView_super_editor_mod, settings_textView_super_editor_open_api_switch, settings_textView_app_theme_open_api;

    public AbilitySettingsLayoutTree(UIAbility ability) {
        super(ability);
    }

    @Override
    public int getLayoutID() {
        return layout.ability_settings_layout;
    }

    @Override
    public void init() {
        settings_back = findViewById(id.settings_back);
        settings_back_viewGroup = findViewById(id.settings_back_viewGroup);
        settings_switchButton_super_editor_switch = findViewById(id.settings_switchButton_super_editor_switch);
        settings_switchButton_super_editor_open_api_switch = findViewById(id.settings_switchButton_super_editor_open_api_switch);
        settings_switchButton_app_theme_open_api_switch = findViewById(id.settings_switchButton_app_theme_open_api_switch);
        settings_imageView_app_theme_go = findViewById(id.settings_imageView_app_theme_go);
        settings_spinner_super_editor_mod = findViewById(id.settings_spinner_super_editor_mod);
        settings_textView_super_editor_mod = findViewById(id.settings_textView_super_editor_mod);
        settings_textView_super_editor_open_api_switch = findViewById(id.settings_textView_super_editor_open_api_switch);
        settings_textView_app_theme_open_api = findViewById(id.settings_textView_app_theme_open_api);

    }
}
