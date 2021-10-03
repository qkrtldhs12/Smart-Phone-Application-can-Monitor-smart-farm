package com.example.spam_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private List<Device_Data> device;
    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(DeviceViewAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }


    public class  CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView connected;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item2_name);
            this.connected = (TextView) view.findViewById(R.id.item2_option1);
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

    public DeviceViewAdapter(List<Device_Data> list) {
        this.device = list;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            // 리사이클러뷰 첫 번째 아이템
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deviceview_item_head, parent, false);
            return new CustomViewHolder_Head(view);
        }
        else if (viewType == 1) {
            // 디바이스 정보
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deviceview_item, parent, false);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }
        else {
            // 리사이클러뷰 마지막 아이템
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deviceview_item_tail, parent, false);
            return new CustomViewHolder_Tail(view);
        }
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Device_Data data = device.get(position);
        if(data.getViewtype() == 1) {
            ((CustomViewHolder)holder).name.setText(data.getName());
            ((CustomViewHolder)holder).connected.setText(data.getConnected());
        }
    }

    @Override
    public int getItemCount() {
        return (null != device ? device.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
       return device.get(position).getViewtype();
    }



}
