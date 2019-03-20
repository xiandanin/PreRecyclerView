package com.dyhdyh.view.prerecyclerview.example.adapter;


import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyhdyh.view.prerecyclerview.example.R;

import java.util.List;

/**
 * author  dengyuhan
 * created 2017/7/4 15:16
 */
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.Holder> {
    private List<ExampleModel> data;
    private boolean staggeredGrid;

    public ExampleAdapter(List<ExampleModel> data) {
        this(data, false);
    }

    public ExampleAdapter(List<ExampleModel> data, boolean staggeredGrid) {
        this.data = data;
        this.staggeredGrid = staggeredGrid;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_example, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ExampleModel model = data.get(position);
        holder.tv.setText(model.getTitle());
        if (staggeredGrid && position % 2 == 0) {
            holder.iv.getLayoutParams().height = holder.itemView.getResources().getDimensionPixelSize(R.dimen.image_max_height);
        } else {
            holder.iv.getLayoutParams().height = holder.itemView.getResources().getDimensionPixelSize(R.dimen.image_height);
        }
        holder.iv.setImageDrawable(new ColorDrawable(model.getColor()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setStaggeredGrid(boolean staggeredGrid) {
        this.staggeredGrid = staggeredGrid;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    public List<ExampleModel> getData() {
        return data;
    }

    public void setData(List<ExampleModel> data) {
        this.data = data;
    }
}
