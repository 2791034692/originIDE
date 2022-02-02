package oms.view.list.recycle;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MultiAdapter extends RecyclerView.Adapter<ViewHolder>{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }
    @Override
    public int getItemCount() {
        return 0;
    }
    private int layoutId;
    void setLayout(int t){
        this.layoutId = t;
    }

}
