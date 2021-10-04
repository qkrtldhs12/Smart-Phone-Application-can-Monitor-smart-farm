package com.example.spam_project.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spam_project.CellViewAdapter;
import com.example.spam_project.Cell_Data;
import com.example.spam_project.DB_Controller;
import com.example.spam_project.DeviceViewAdapter;
import com.example.spam_project.Device_Data;
import com.example.spam_project.MainActivity;
import com.example.spam_project.R;

import java.util.ArrayList;
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

    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        Bundle bundle = getArguments();
        String User_Email = bundle.getString("User_Email");

        //List<Device_Data> device = new ArrayList<>();
        //device.add(new Device_Data(0));
        // 파이어베이스 환경 데이터 읽어오기
        List<Device_Data> device =  DB_Controller.Call_Device(User_Email);
        //device.add(new Device_Data(2));

        deviceViewAdapter = new DeviceViewAdapter(device);
        deviceViewAdapter.setOnItemClickListener(new DeviceViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Intent intent = new Intent(getActivity(), TestActivity.class);
                // 프래그먼트상에선 context가 존재하지 않기 때문에 getActivty()를 통해 실행시킴
                //startActivity(intent);
            }
        });
        recyclerView.setAdapter(deviceViewAdapter);
        return rootView;
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
