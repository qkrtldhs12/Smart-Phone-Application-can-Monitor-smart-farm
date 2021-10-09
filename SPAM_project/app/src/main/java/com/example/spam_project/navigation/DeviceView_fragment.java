package com.example.spam_project.navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.spam_project.Add_DeviceActivity;
import com.example.spam_project.DeviceControlActivity;
import com.example.spam_project.DeviceViewAdapter;
import com.example.spam_project.Device_Data;
import com.example.spam_project.MainActivity;
import com.example.spam_project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class DeviceView_fragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DeviceViewAdapter deviceViewAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ImageView current_image;
    String model_id;



    public static List<Device_Data> device;
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);


        deviceViewAdapter = new DeviceViewAdapter(device);

        //롱클릭
        deviceViewAdapter.setOnItemLongClickListener(new DeviceViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("연결된 환경 제거")
                        .setMessage("삭제하시겠습니까?")
                        .setIcon(R.drawable.ic_alarm)
                        .setNegativeButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Item 삭제
                                Device_Data Data = deviceViewAdapter.getItem(position);
                                String delete = Data.getModel_id();
                                device.remove(position);
                                deviceViewAdapter.notifyItemRemoved(position);
                                deviceViewAdapter.notifyItemRangeChanged(position, device.size());
                                db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(delete)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                //DB 삭제 성공
                                            }
                                        });
                            }
                        })
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 삭제 취소
                            }
                        })
                        .show();
            }
        });

        // 짧은 클릭
        deviceViewAdapter.setOnItemClickListener(new DeviceViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(device.size()-1 == position) {
                    Intent intent = new Intent(getActivity(), Add_DeviceActivity.class);
                    startActivity(intent);
                }
                else{
                    // 제어 페이지
                    Intent intent = new Intent(getActivity(), DeviceControlActivity.class);
                    intent.putExtra("model_id", deviceViewAdapter.getItem(position).getModel_id());
                    intent.putExtra("name", deviceViewAdapter.getItem(position).getName());
                    intent.putExtra("connected", deviceViewAdapter.getItem(position).getConnected());
                    intent.putExtra("door", deviceViewAdapter.getItem(position).getDoor());
                    intent.putExtra("heat", deviceViewAdapter.getItem(position).getHeat());
                    intent.putExtra("humidifier", deviceViewAdapter.getItem(position).getHumidifier());
                    intent.putExtra("light", deviceViewAdapter.getItem(position).getLight());
                    intent.putExtra("vent", deviceViewAdapter.getItem(position).getVent());
                    startActivity(intent);
                }
            }
        });

        // 사진 클릭
        deviceViewAdapter.setOnImageClickListener(new DeviceViewAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(View view, int position) {
                current_image = (ImageView) view.findViewById(R.id.item2_image);
                model_id = deviceViewAdapter.getItem(position).getModel_id();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });

        recyclerView.setAdapter(deviceViewAdapter);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getData();
                    Glide.with(getActivity()).load(uri).into(current_image);
                    String path = "image/" + model_id + ".png";
                    UploadTask uploadTask = storageRef.child(path).putFile(uri);
                }
                catch (Exception e) {

                }
            }
            else if(resultCode == RESULT_CANCELED) {
                //사진 선택 취소
            }
       }
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
