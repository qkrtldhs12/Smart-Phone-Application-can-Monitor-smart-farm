package com.example.spam_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CellViewAdapter extends RecyclerView.Adapter<CellViewAdapter.CustomViewHolder> {

    private List<Cell_Data> cell;

    public class  CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView humi;
        protected TextView lumi;
        protected TextView soil;
        protected TextView temp;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item_name);
            this.humi = (TextView) view.findViewById(R.id.item_option1);
            this.lumi = (TextView) view.findViewById(R.id.item_option2);
            this.soil = (TextView) view.findViewById(R.id.item_option3);
            this.temp = (TextView) view.findViewById(R.id.item_option4);

        }
    }
    public class  CustomViewHolder_Head extends RecyclerView.ViewHolder{
        public CustomViewHolder_Head(View view) {
            super(view);
        }
    }

    public CellViewAdapter(List<Cell_Data> list) {
        this.cell = list;
    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cellview_item_head, parent, false);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cellview_item, parent, false);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
        Cell_Data data = cell.get(position);
        if(data.getViewtype() != 0) {
            holder.name.setText(data.getName());
            holder.humi.setText(data.getHumi());
            holder.lumi.setText(data.getLumi());
            holder.soil.setText(data.getSoil());
            holder.temp.setText(data.getTemp());
        }
    }

    @Override
    public int getItemCount() {
        return (null != cell ? cell.size() : 0);
    }

    @Override
    public int getItemViewType(int position) { return cell.get(position).getViewtype(); }


}
