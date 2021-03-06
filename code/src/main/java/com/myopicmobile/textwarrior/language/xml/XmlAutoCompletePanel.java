package com.myopicmobile.textwarrior.language.xml;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Filter;
import android.widget.TextView;

import com.myopicmobile.textwarrior.R;
import com.myopicmobile.textwarrior.android.FreeScrollingTextField;
import com.myopicmobile.textwarrior.base.BasePanel;
import com.myopicmobile.textwarrior.bean.ListItem;

import java.util.ArrayList;
import java.util.HashMap;


public class XmlAutoCompletePanel extends BasePanel {

    public final int TYPE_METHOD = 0;
    public final int TYPE_EVENT = 1;
    private final int mPadding = 20;
    public int markedType;
    public CharSequence mText;
    public String markedText = "";
    private Filter mFilter;

    public XmlAutoCompletePanel(FreeScrollingTextField field) {
        super(field);
        setAdapter(new XmlPanelAdapter(field.getContext()));
    }

    public XmlAutoCompletePanel(Context context) {
        super(context);
    }

    public XmlAutoCompletePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XmlAutoCompletePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XmlAutoCompletePanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public XmlPanelAdapter getAdapter() {
        return (XmlPanelAdapter) super.getAdapter();
    }

    @Override
    public void select(int pos) {
        FreeScrollingTextField field = getTextField();
        XmlPanelAdapter adapter = getAdapter();
        View view = adapter.getView(pos, null, null);
        TextView textView = (TextView) view.findViewById(R.id.auto_panel_text);
        String text = textView.getText().toString();
        String commitText = null;
        boolean isFunc = text.contains("(");
        boolean key = false;
        boolean needFormat = false;
            /*if (text.equals("????????????")) {
                commitText = "???????????? " + mardedLoopVar + "= 0 ??? 10\n?????? ??????";
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
            } else if (text.equals("????????????")) {
                commitText = "???????????? ????????? ??? " +
                        markedVarType.replace("[", "")
                                .replace("]", "") + " ??? " + mardedLoopVar + "\n?????? ??????";
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

    public class XmlPanelAdapter extends BasePanelAdapter {

        public XmlPanelAdapter(Context context) {
            super(context);
        }

        @Override
        public Filter getFilter() {
            mFilter = new Filter() {

                public String getVar(HashMap<String, String> map, String name) {
                    return map.containsKey(name) ? map.get(name) : name;
                }

                /**
                 * ???????????????????????????????????????????????????
                 */
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    // ??????????????????
                    // ???????????????FilterResults?????????????????????
                    ArrayList<String> buf = new ArrayList<>();
                    String keyword = String.valueOf(constraint).toLowerCase();
                    mText = keyword;
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = buf;   // results????????????????????????
                    filterResults.count = buf.size();  // ????????????
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0 && !isSet()) {
                        // ??????????????????????????????????????????
                        clearResults();   // ???????????????
                        ArrayList<String> stringArrayList = (ArrayList<String>) results.values;
                        for (int i = 0; i < stringArrayList.size(); i++) {
                            String itemText = stringArrayList.get(i);
                            if (itemText.contains("(")) {
                                addResult(new ListItem(getDefaultBitmap(), itemText));
                            } else {
                                addResult(new ListItem((CharSequence) null, itemText));
                            }
                        }
                        int y = getTextField().getCaretY() + getTextField().rowHeight() / 2 - getTextField().getScrollY();
                        setHeight(getItemHeight() * Math.min(4, results.count));
                        setHorizontalOffset(mPadding);
                        setWidth(getTextField().getWidth() - mPadding * 2);
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
