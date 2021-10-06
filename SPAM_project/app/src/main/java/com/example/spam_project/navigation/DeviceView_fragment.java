package com.example.spam_project.navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spam_project.DeviceViewAdapter;
import com.example.spam_project.Device_Data;
import com.example.spam_project.MainActivity;
import com.example.spam_project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceView_fragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DeviceViewAdapter deviceViewAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    public static List<Device_Data> device;
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);


        deviceViewAdapter = new DeviceViewAdapter(device);
        // deviceViewAdepter가 Call 이후에 생성되어야 함

        //롱클릭
        deviceViewAdapter.setOnItemLongClickListener(new DeviceViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                System.out.println("기이이이이이인 클릭");
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
                                System.out.println("확인창 테스트 : YES");
                                //삭제 취소
                            }
                        })
                        .show();
            }
        });

        // 짧은 클릭
        deviceViewAdapter.setOnItemClickListener(new DeviceViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("짧은 클릭");
                //데이터 추가
            }
        });
        recyclerView.setAdapter(deviceViewAdapter);
        return rootView;
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
