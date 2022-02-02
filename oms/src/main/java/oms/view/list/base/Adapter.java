package oms.view.list.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter<T> extends BaseAdapter {
    private List<T> arrayList;
    private int layout;
    private Context context;
    private OnLoadItem<T> onLoadItem = null;


    public Adapter(Context context, List<T> list, int layout) {
        this.arrayList = list;
        this.context = context;
        this.layout = layout;
    }

    public Adapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setList(List<T> list) {
        this.arrayList = list;
    }

    public void addOnLoadItem(OnLoadItem onLoadItem) {
        this.onLoadItem = onLoadItem;
    }


    public T remove(int t) {
        return arrayList.remove(t);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public T getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void put(T e) {
        this.arrayList.add(e);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        if (onLoadItem != null) {
            onLoadItem.onLoad(viewHolder, position, getItem(position));
        }
        return viewHolder.getConvertView();
    }

    public void clear() {
        arrayList.clear();
    }

    public void changing() {
        this.notifyDataSetChanged();
    }

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.get(context, convertView, parent, layout, position);
    }

    public interface OnLoadItem<T> {
        void onLoad(ViewHolder holder, int point, T data);
    }

    public static class ViewHolder {
        private final SparseArray<View> sparseArray;
        private final int position;
        private final View mConvertView;

        private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            this.position = position;
            this.sparseArray = new SparseArray<>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            // setTag
            mConvertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layout, int position) {
            if (convertView == null) {
                return new ViewHolder(context, parent, layout, position);
            }
            return (ViewHolder) convertView.getTag();
        }

        public void setTextViewText(int t, String string) {
            View view = getView(t);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setText(string);
            }
        }

        public void setViewTag(int tag, Object object) {
            View view = getView(tag);
            view.setTag(object);
        }

        public <T extends View> T getView(int viewId) {
            View view = sparseArray.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                sparseArray.put(viewId, view);
            }
            return (T) view;
        }

        public void setImageViewSrc(int t, Drawable string) {
            View view = getView(t);
            if (view instanceof TextView) {
                ImageView textView = (ImageView) view;
                textView.setImageDrawable(string);
            }
        }

        public void setImageViewSrc(int t, Bitmap string) {
            View view = getView(t);
            if (view instanceof TextView) {
                ImageView textView = (ImageView) view;
                textView.setImageBitmap(string);
            }
        }


        protected View getConvertView() {
            return mConvertView;
        }

    }

}
