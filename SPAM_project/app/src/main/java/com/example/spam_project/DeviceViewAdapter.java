package com.example.spam_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceViewAdapter extends RecyclerView.Adapter<DeviceViewAdapter.CustomViewHolder> {

    private List<Device_Data> device;

    public class  CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView connected;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item2_name);
            this.connected = (TextView) view.findViewById(R.id.item2_option1);
        }
    }

    public DeviceViewAdapter(List<Device_Data> list) {
        this.device = list;
    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deviceview_item, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
        Device_Data data = device.get(position);

        holder.name.setText(data.getName());
        holder.connected.setText(data.getConnected());
    }

    @Override
    public int getItemCount() {
        return (null != device ? device.size() : 0);
    }


}
