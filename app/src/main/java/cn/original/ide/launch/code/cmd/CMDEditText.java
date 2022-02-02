package cn.original.ide.launch.code.cmd;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;

import cn.original.ide.R;
import cn.original.ide.module.System;
import cn.original.ide.module.ability.CMDAbility;

public class CMDEditText extends AppCompatEditText {

    public CMDEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setText(R.string.editor_console_introduce);
        addText("\n命令 >");
        setHorizontallyScrolling(true);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (getCurrentCursorLine() == 0) {
                        java.lang.System.err.println("该内容为控制台标示语句，不得删除！");
                        return true;
                    } else {
                        java.lang.System.out.println(getSelectionStart());
                    }
                    return false;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String[] strings = getText().toString().split("\n");
                    String a = strings[strings.length - 1].trim();
                    String cmd;
                    if (a.length() <= 4) {
                        java.lang.System.err.println("命令不能为空！");
                        return true;
                    } else {
                        cmd = a.substring(4).trim();
                        String[] strings2 = cmd.split(" ");
                        String hand = strings2[0];
                        if (hand.length() == 1 && hand.startsWith("$")) {
                            if (strings2.length < 2) {
                                java.lang.System.err.println("错误 缺少命令头");
                                return true;
                            }
                            ArrayMap<String, CMDAbility> cmdAbilityArrayMap = System.with(CMDEditText.this.getContext()).getCmdAbilityArrayMap();
                            CMDAbility ability = cmdAbilityArrayMap.get(strings2[1]);
                            if (ability == null) {
                                java.lang.System.err.println("错误 该命令无可执行对象！");
                            } else {
                                ArrayList<String> strings1 = new ArrayList<>();
                                for (int t = 0; t < strings2.length; t++) {
                                    if (t > 1) {
                                        strings1.add(strings2[t]);
                                    }
                                    continue;
                                }
                                ability.main(strings1.toArray(new String[]{}));
                            }
                        } else {
                            java.lang.System.err.println("错误 由于版本调整原子灵动自1.3.769版本开始不再支持直接调用系统命令！如有需要请安装模组。");
                        }
                    }
                    addText("\n命令 >");
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void print(Object object) {
        java.lang.System.out.println(object);
    }

    private int getCurrentCursorLine() {
        int selectionStart = Selection.getSelectionStart(this.getText());
        Layout layout = this.getLayout();
        if (selectionStart != -1) {
            return layout.getLineForOffset(selectionStart) + 1;
        }
        return -1;
    }

    private void addText(String string) {
        int start = getSelectionStart();
        int end = start + string.length();
        setText(getText() + string);
        setSelection(end);
    }


}
