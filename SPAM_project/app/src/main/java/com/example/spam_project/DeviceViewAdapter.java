package com.example.spam_project;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spam_project.navigation.DeviceView_fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
    public interface OnImageClickListener {
        void onImageClick(View view, int position);
    }


    private List<Device_Data> device;
    private OnItemClickListener mListener = null;
    private OnItemLongClickListener mLongListener = null;
    private OnImageClickListener mImageListener = null;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) { this.mLongListener = listener; }
    public void setOnImageClickListener(OnImageClickListener listener) {this.mImageListener = listener;}
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public class  CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView connected;
        protected TextView model_id;
        protected TextView door;
        protected TextView heat;
        protected TextView humidifier;
        protected TextView light;
        protected TextView vent;
        protected ImageView image;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item2_name);
            this.connected = (TextView) view.findViewById(R.id.item2_connected);
            this.model_id = (TextView) view.findViewById(R.id.item2_model_id);
            this.door = (TextView) view.findViewById(R.id.item2_door);
            this.heat = (TextView) view.findViewById(R.id.item2_heat);
            this.humidifier = (TextView) view.findViewById(R.id.item2_humidifier);
            this.light = (TextView) view.findViewById(R.id.item2_light);
            this.vent = (TextView) view.findViewById(R.id.item2_vent);
            this.image = (ImageView) view.findViewById(R.id.item2_image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        mImageListener.onImageClick(view, position);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mLongListener != null) {
                            mLongListener.onItemLongClick(view, position);
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
            ((CustomViewHolder)holder).name.setText("이름: "+data.getName());
            ((CustomViewHolder)holder).connected.setText("화분 수: "+data.getConnected());
            ((CustomViewHolder)holder).model_id.setText("ID: "+data.getModel_id());
            ((CustomViewHolder)holder).door.setText("문: "+data.getDoor());
            ((CustomViewHolder)holder).heat.setText("난방: "+data.getHeat());
            ((CustomViewHolder)holder).humidifier.setText("가습기: "+data.getHumidifier());
            ((CustomViewHolder)holder).light.setText("조명: "+data.getLight());
            ((CustomViewHolder)holder).vent.setText("환풍기: "+data.getVent());
            String path = "image/" + data.getModel_id() + ".png";
            StorageReference pathRef = storage.getReference().child(path);

            pathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(holder.itemView.getContext()).load(uri).into(((CustomViewHolder) holder).image);
                }
            });

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

    public Device_Data getItem(int position) {
        return device.get(position);
    }



}
