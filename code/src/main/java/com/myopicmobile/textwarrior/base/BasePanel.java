package com.myopicmobile.textwarrior.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.myopicmobile.textwarrior.android.FreeScrollingTextField;
import com.myopicmobile.textwarrior.bean.ListItem;
import com.myopicmobile.textwarrior.common.Flag;
import com.myopicmobile.textwarrior.common.PinyinTool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.original.ide.launch.code.R;

@SuppressLint("NewApi")
public class BasePanel extends ListPopupWindow {

    public int updateLine;
    private BaseLanguage language;
    private BasePanelAdapter adapter;
    private FreeScrollingTextField mTextField;
    private Context context;
    private SharedPreferences prefs;
    private GradientDrawable mDrawable;
    private Filter mFilter;
    private int mTextColor, mBackgroundColor, mHorizontal, mVertical;
    private int mWidth, mHeight = 0;
    private boolean isDark;
    private boolean isShow;

    public BasePanel(FreeScrollingTextField field) {
        super(field.getContext());
        this.mTextField = field;
        this.context = field.getContext();
        init();
    }

    public BasePanel(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BasePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BasePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public BasePanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    public BasePanelAdapter getAdapter() {
        return this.adapter;
    }

    public void setAdapter(BasePanelAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
        mFilter = adapter.getFilter();
    }

    public BaseLanguage getLanguage() {
        return this.language;
    }

    public void setLanguage(BaseLanguage language) {
        this.language = language;
    }

    public FreeScrollingTextField getTextField() {
        return this.mTextField;
    }

    public void setTextField(FreeScrollingTextField field) {
        this.mTextField = field;
    }

    public boolean isDark() {
        return this.isDark;
    }

    public void setDark(boolean bool) {
        this.isDark = bool;
    }

    public void setTextColor(int color) {
        mTextColor = color;
        mDrawable.setStroke(1, color);
        this.setBackgroundDrawable(mDrawable);
    }


    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        mDrawable.setColor(color);
        this.setBackgroundDrawable(mDrawable);
    }

    public void setBackground(Drawable color) {
        this.setBackgroundDrawable(color);
    }

    @Override
    public void setHeight(int height) {
        if (mHeight != height) {
            mHeight = height;
            super.setHeight(height);
        }
    }

    @Override
    public void setHorizontalOffset(int horizontal) {
        horizontal = Math.min(horizontal, mTextField.getWidth() / 2);
        if (mHorizontal != horizontal) {
            mHeight = horizontal;
            super.setHorizontalOffset(horizontal);
        }
    }

    @Override
    public void setVerticalOffset(int verticalOffset) {
        //verticalOffset=Math.min(verticalOffset,_textField.getWidth()/2);
        int max = 0 - this.getHeight();
        if (verticalOffset > max) {
            mTextField.scrollBy(0, verticalOffset - max);
            verticalOffset = max;
        }
        if (mVertical != verticalOffset) {
            mVertical = verticalOffset;
            super.setVerticalOffset(verticalOffset);
        }
    }

    public void update(int line, CharSequence constraint) {
        adapter.restart();
        mFilter.filter(constraint);
        this.updateLine = line;
    }

    public boolean isShow() {
        return super.isShowing();
    }

    @Override
    public void show() {
        super.show();
        getListView().setFadingEdgeLength(0);
        isShow = true;
    }

    public void dismiss() {
        if (isShowing()) {
            isShow = false;
            super.dismiss();
        }
    }

    public void select(int pos) {
        View view = adapter.getView(pos, null, null);
        TextView textView = (TextView) view.findViewById(R.id.auto_panel_text);
        String text = textView.getText().toString();
        mTextField.replaceText(mTextField.getCaretPosition() - text.length(), text.length(), text);
        adapter.abort();
        dismiss();
    }

    public void selectFirst() {
        select(0);
    }

    private void init() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        isDark = prefs.getBoolean("theme", false);
        this.adapter = new BasePanelAdapter(context);
        this.setAdapter(adapter);
        this.setContentWidth(WRAP_CONTENT);
        this.setAnchorView(mTextField);
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimary,
        });
        int backgroundColor = array.getColor(0, 0xFF00FF);
        int textColor = array.getColor(1, 0xFF00FF);
        array.recycle();
        mDrawable = new GradientDrawable();
        mDrawable.setColor(backgroundColor);
        mDrawable.setCornerRadius(4);
        mDrawable.setStroke(1, textColor);
        setTextColor(textColor);
        this.setBackgroundDrawable(mDrawable);
        this.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select(position);
            }
        });
    }

    public class BasePanelAdapter extends BaseAdapter implements Filterable {

        final int GB_SP_DIFF = 160;
        final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302,
                2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
                4086, 4390, 4558, 4684, 4925, 5249, 5600};
        final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
                'y', 'z'};
        private CharSequence varBitmap, methodBitmap, eventBitmap, propertyGetBitmap, propertySetBitmap;
        private Context context;
        private DisplayMetrics dm;
        private CharSequence sequence;
        private Flag flag;
        private int itemHeight;
        private List<ListItem> resultItems;

        public BasePanelAdapter(Context context) {
            this.context = context;
            dm = context.getResources().getDisplayMetrics();
            sequence = "";
            flag = new Flag();
            resultItems = new ArrayList<>();
        }

        /*public BaseLanguage getLanguage() {
            return this.language;
        }

        public void setLanguage(BaseLanguage language) {
            this.language = language;
        }*/

        public void addResult(ListItem resultItem) {
            resultItems.add(resultItem);
        }

        public void clearResults() {
            resultItems.clear();
        }

        public void setBitmap(CharSequence bitmap) {
            this.sequence = bitmap;
        }

        public CharSequence getDefaultBitmap() {
            return this.sequence;
        }

        public CharSequence getVarBitmap() {
            if (varBitmap == null) {
                varBitmap = "变";
            }
            return varBitmap;
        }

        public CharSequence getMethodBitmap() {
            if (methodBitmap == null) {
                methodBitmap = "方";
            }
            return methodBitmap;
        }

        public CharSequence getEventBitmap() {
            if (eventBitmap == null) {
                eventBitmap = "事";
            }
            return eventBitmap;
        }

        public CharSequence getPropertyGetBitmap() {
            if (propertyGetBitmap == null) {
                propertyGetBitmap = "取";
            }
            return propertyGetBitmap;
        }

        public CharSequence getPropertySetBitmap() {
            if (propertySetBitmap == null) {
                propertySetBitmap = "设";
            }
            return propertySetBitmap;
        }

        public Bitmap createBitmap(int resId) {
            return BitmapFactory.decodeResource(context.getResources(), resId);
        }

        public void abort() {
            flag.set();
        }

        public void restart() {
            flag.clear();
        }

        public boolean isSet() {
            return flag.isSet();
        }

        public int getItemHeight() {
            if (itemHeight != 0)
                return itemHeight;
            LayoutInflater inflater = LayoutInflater.from(context);
            View rootView = inflater.inflate(R.layout.panel_completion_item, null);
            rootView.measure(0, 0);
            itemHeight = rootView.getMeasuredHeight();
            return itemHeight;
        }

        @SuppressLint("WrongConstant")
        private int dp(float n) {
            return (int) TypedValue.applyDimension(1, n, dm);
        }

        @Override
        public int getCount() {
            return resultItems.size();
        }

        @Override
        public ListItem getItem(int position) {
            return resultItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                @SuppressLint("InflateParams") View rootView = LayoutInflater.from(context).inflate(R.layout.panel_completion_item, null);
                view = rootView;
            } else {
                view = convertView;
            }
            TextView textView = view.findViewById(R.id.auto_panel_text);
            TextView imageView = view.findViewById(R.id.auto_panel_text_modification);
            TextView textView1 = view.findViewById(R.id.auto_panel_text_object);
            String text = getItem(position).getText();
            CharSequence text2 = getItem(position).getCharSequence();
            CharSequence text3 = getItem(position).getCharSequence2();
            try {
                textView1.setText(text);
                imageView.setText(text2);
                if (text3 == null) {
                    textView.setText(text);
                } else {
                    textView.setText(text3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }

        @Override
        public Filter getFilter() {
            return null;
        }

        public String getAllSpells(String chs) {
            return PinyinTool.getPinyinForWords(chs);
        }

        public String getSpells(String characters) {
            if (characters.startsWith("调试输出"))
                return "tssc";
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < characters.length(); i++) {
                char ch = characters.charAt(i);
                if ((ch >> 7) == 0) {
                    buffer.append(ch);
                } else {
                    char spell = getFirstLetter(ch);
                    buffer.append(spell);
                }
            }
            return buffer.toString();
        }

        public Character getFirstLetter(char ch) {
            if (ch == '弹')
                return 't';
            if (ch == '乐')
                return 'y';
            if (ch == '调')
                return 'd';
            byte[] uniCode = null;
            try {
                uniCode = String.valueOf(ch).getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
            if (uniCode[0] < 128 && uniCode[0] > 0) {
                return null;
            } else {
                return convert(uniCode);
            }
        }

        private char convert(byte[] bytes) {
            char result = '-';
            int secPosValue = 0;
            int i;
            for (i = 0; i < bytes.length; i++) {
                bytes[i] -= GB_SP_DIFF;
            }
            secPosValue = bytes[0] * 100 + bytes[1];
            for (i = 0; i < 23; i++) {
                if (secPosValue >= secPosValueList[i]
                        && secPosValue < secPosValueList[i + 1]) {
                    result = firstLetter[i];
                    break;
                }
            }
            return result;
        }

    }


}
