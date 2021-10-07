package com.example.spam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DeviceControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        Intent intent = getIntent();
        System.out.println("model_id : " + intent.getStringExtra("model_id"));
        System.out.println("name : " + intent.getStringExtra("name"));
        System.out.println("connected : " + intent.getStringExtra("connected"));
        System.out.println("door : " + intent.getStringExtra("door"));
        System.out.println("heat : " + intent.getStringExtra("heat"));
        System.out.println("humidifier : " + intent.getStringExtra("humidifier"));
        System.out.println("light : " + intent.getStringExtra("light"));
        System.out.println("vent : " + intent.getStringExtra("vent"));

        /*intent.putExtra("model_id", deviceViewAdapter.getItem(position).getModel_id());
        intent.putExtra("name", deviceViewAdapter.getItem(position).getName());
        intent.putExtra("connected", deviceViewAdapter.getItem(position).getConnected());
        intent.putExtra("door", deviceViewAdapter.getItem(position).getDoor());
        intent.putExtra("heat", deviceViewAdapter.getItem(position).getHeat());
        intent.putExtra("humidifier", deviceViewAdapter.getItem(position).getHumidifier());
        intent.putExtra("light", deviceViewAdapter.getItem(position).getLight());
        intent.putExtra("vent", deviceViewAdapter.getItem(position).getVent());*/

        //control_top에 디바이스 그림, 디바이스 이름, UnDO 버튼, 제어 전송 버튼
        //control_bottom에 제어 옵션 5가지 ON/OFF 상태에 따라 이미지 버튼으로 구현
    }
}