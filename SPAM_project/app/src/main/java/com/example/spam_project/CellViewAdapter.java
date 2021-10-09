package com.example.spam_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CellViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, int position);
    }

    private List<Cell_Data> cell;
    private CellViewAdapter.OnItemClickListener mListener = null;
    private OnItemLongClickListener mLongListener = null;
    public void setOnItemClickListener(CellViewAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mLongListener = listener;
    }

    public class  CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView humi;
        protected TextView soil;
        protected TextView temp;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item_name);
            this.humi = (TextView) view.findViewById(R.id.item_option1);
            this.soil = (TextView) view.findViewById(R.id.item_option2);
            this.temp = (TextView) view.findViewById(R.id.item_option3);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mLongListener != null) {
                            mLongListener.OnItemLongClick(view, position);
                        }
                    }
                    return true;
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null) {
                            mListener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
    }
    public class  CustomViewHolder_Head extends RecyclerView.ViewHolder{
        public CustomViewHolder_Head(View view) {
            super(view);
        }
    }
    public class  CustomViewHolder_Tail extends RecyclerView.ViewHolder{
        public CustomViewHolder_Tail(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null) {
                            mListener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
    }


    public CellViewAdapter(List<Cell_Data> list) {
        this.cell = list;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cellview_item_head, parent, false);
            return new CellViewAdapter.CustomViewHolder_Head(view);
        }
        else if(viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deviceview_item_tail, parent, false);
            return new CellViewAdapter.CustomViewHolder_Tail(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cellview_item, parent, false);
            RecyclerView.ViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder  holder, int position) {
        Cell_Data data = cell.get(position);
        if(data.getViewtype() == 1) {
            ((CellViewAdapter.CustomViewHolder)holder).name.setText(data.getName());
            ((CellViewAdapter.CustomViewHolder)holder).humi.setText(data.getHumi());
            ((CellViewAdapter.CustomViewHolder)holder).soil.setText(data.getSoil());
            ((CellViewAdapter.CustomViewHolder)holder).temp.setText(data.getTemp());
        }
    }

    @Override
    public int getItemCount() {
        return (null != cell ? cell.size() : 0);
    }

    @Override
    public int getItemViewType(int position) { return cell.get(position).getViewtype(); }

    public Cell_Data getItem(int position) {
        return cell.get(position);
    }


}
