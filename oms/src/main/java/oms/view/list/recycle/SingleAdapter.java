package oms.view.list.recycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter<ViewHolder>{
    private int layoutId;
    private List list;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layoutId,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    void setLayout(int t){
       this.layoutId = t;
    }
    void setData(List list){
        this.list = list;
    }
}
