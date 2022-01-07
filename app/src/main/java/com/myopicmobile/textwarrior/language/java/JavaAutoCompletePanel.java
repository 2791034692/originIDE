package com.myopicmobile.textwarrior.language.java;

import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Filter;
import android.widget.TextView;

import com.myopicmobile.textwarrior.android.FreeScrollingTextField;
import com.myopicmobile.textwarrior.base.BaseLanguage;
import com.myopicmobile.textwarrior.base.BasePanel;
import com.myopicmobile.textwarrior.bean.ListItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.original.ide.launch.code.R;

public class JavaAutoCompletePanel extends BasePanel {

    private final int mPadding = 20;
    public final int TYPE_METHOD = 0;
    public final int TYPE_EVENT = 1;
    public int markedType;
    public CharSequence mText;
    public String markedText = "";
    private Filter mFilter;
    private Context context;

    public JavaAutoCompletePanel(FreeScrollingTextField field) {
        super(field);
        setAdapter(new JavaPanelAdapter(this.context = field.getContext()));
    }

    public JavaAutoCompletePanel(Context context) {
        super(context);
    }

    public JavaAutoCompletePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JavaAutoCompletePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JavaAutoCompletePanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setLanguage(BaseLanguage language) {
        super.setLanguage(language);
        init(context);
    }

    private String readAssets(Context context, String filename) {
        try {
            InputStream inputstream = context.getAssets().open(filename);
            return readStream(inputstream);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //读取流
    private String readStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        boolean first = true;
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (first) {
                first = false;
                content.append(line);
            } else {
                content.append('\n').append(line);
            }
        }
        br.close();
        return content.toString();
    }

    private void init(Context context) {
        String text = readAssets(context, "contents/java.api").trim();
        String[] classes = text.split("\\,");
        String[] simpleClassNames = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            simpleClassNames[i] = classes[i].substring(classes[i].lastIndexOf(".") + 1);
        }
        getLanguage().addNames(simpleClassNames);
    }

    @Override
    public LanguageJava getLanguage() {
        return (LanguageJava) super.getLanguage();
    }

    @Override
    public JavaPanelAdapter getAdapter() {
        return (JavaPanelAdapter) super.getAdapter();
    }

    @Override
    public void select(int pos) {
        FreeScrollingTextField field = getTextField();
        JavaPanelAdapter adapter = getAdapter();
        View view = adapter.getView(pos, null, null);
        TextView textView = (TextView) view.findViewById(R.id.auto_panel_text_object);
        String text = textView.getText().toString();
        String commitText = null;
        boolean isFunc = text.contains("(");
        boolean key = false;
        boolean needFormat = false;
            /*if (text.equals("变量循环")) {
                commitText = "变量循环 " + mardedLoopVar + "= 0 至 10\n结束 循环";
                needFormat = true;
                markedText = commitText;
                field.replaceText(field.getCaretPosition() -
                                _constraint.length() -
                                mardedLoopVar.length() - 1,
                        _constraint.length() +
                                mardedLoopVar.length() + 1, commitText);
                _adapter.abort();
                dismiss();
                field.format();
                return;
            } else if (text.equals("遍历循环")) {
                commitText = "遍历循环 变量名 为 " +
                        markedVarType.replace("[", "")
                                .replace("]", "") + " 从 " + mardedLoopVar + "\n结束 循环";
                needFormat = true;
                markedText = commitText;
                field.replaceText(field.getCaretPosition() -
                                _constraint.length() -
                                mardedLoopVar.length() - 1,
                        _constraint.length() +
                                mardedLoopVar.length() + 1, commitText);
                _adapter.abort();
                dismiss();
                field.format();
                return;
            } else {*/
        if (isFunc) {
            needFormat = false;
            if (markedType == TYPE_METHOD) {
                if (text.substring(text.indexOf("(") + 1, text.indexOf(")")).equals(""))
                    commitText = text.substring(0, text.indexOf('(')) + "()";
                else
                    commitText = text.substring(0, text.indexOf('(')) + "(";
            } else {
                key = true;
                commitText = text;
            }
        } else if (text.contains(" :")) {
            commitText = text.substring(0, text.indexOf(" :"));
        } else {
            commitText = text;
        }
        markedText = commitText;
        field.replaceText(field.getCaretPosition() - mText.length(), mText.length(), commitText);
        adapter.abort();
        dismiss();
    }

    public class JavaPanelAdapter extends BasePanelAdapter {
        private ArrayMap<CharSequence, String> sequenceStringArrayMap = new ArrayMap<>();

        public JavaPanelAdapter(Context context) {
            super(context);
            sequenceStringArrayMap.put("冒号", ":");
            sequenceStringArrayMap.put("逗号", ",");
            sequenceStringArrayMap.put("dh", ",");
            sequenceStringArrayMap.put("mh", ":");
            sequenceStringArrayMap.put("双引号", "\"");
            sequenceStringArrayMap.put("syh", "\"");
            sequenceStringArrayMap.put("问号", "?");
            sequenceStringArrayMap.put("wh", "?");
            sequenceStringArrayMap.put("感叹号", "!");
            sequenceStringArrayMap.put("gkh", "!");
            sequenceStringArrayMap.put("左大括", "{");
            sequenceStringArrayMap.put("右大括", "}");
            sequenceStringArrayMap.put("zdk", "{");
            sequenceStringArrayMap.put("ydk", "}");
            sequenceStringArrayMap.put("单引号", "'");
            sequenceStringArrayMap.put("dyh", "'");
            sequenceStringArrayMap.put("反斜杠", "\\");
            sequenceStringArrayMap.put("fxg", "\\");
        }

        @Override
        public Filter getFilter() {
            mFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    ArrayList<String> buf = new ArrayList<>();
                    String keyword = String.valueOf(constraint).toLowerCase();
                    for (String kw : getLanguage().getKeywords()) {
                        if (kw.contains(keyword)) {
                            buf.add(kw);
                        }
                    }
                    if (sequenceStringArrayMap.containsKey(constraint)) {
                        buf.add(0, sequenceStringArrayMap.get(constraint));
                    }
                    mText = keyword;
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = buf;
                    filterResults.count = buf.size();
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0 && !isSet()) {
                        clearResults();
                        ArrayList<String> stringArrayList = (ArrayList<String>) results.values;
                        for (int i = 0; i < stringArrayList.size(); i++) {
                            String itemText = stringArrayList.get(i);
                            if (sequenceStringArrayMap.containsValue(itemText)) {
                                addResult(new ListItem("符", itemText));
                                continue;
                            }
                            addResult(new ListItem(null, itemText));
                        }
                        int y = getTextField().getCaretY() + getTextField().rowHeight() / 2 - getTextField().getScrollY();
                        setHeight(getItemHeight() * Math.min(4, results.count));
                        setHorizontalOffset(mPadding);
                        setWidth(getTextField().getWidth() - mPadding * 32);
                        setVerticalOffset(y - getTextField().getHeight());
                        //_textField.getCaretY()-_textField.getScrollY()-_textField.getHeight());
                        notifyDataSetChanged();
                        show();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return mFilter;
        }
    }

}
