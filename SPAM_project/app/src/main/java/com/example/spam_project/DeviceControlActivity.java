package com.example.spam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeviceControlActivity extends AppCompatActivity {

    ImageView door, humidifier, vent, light, heat, image;
    TextView device_name;
    Button undo, request;
    String model_id, connected, name;
    List<String> device_state = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String req_door, req_heat, req_humidifier, req_light, req_vent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        Intent intent = getIntent();
        model_id = intent.getStringExtra("model_id");
        connected = intent.getStringExtra("connected");
        name = intent.getStringExtra("name");

        image = (ImageView)findViewById(R.id.control_device_image);
        String path = "image/" + model_id + ".png";
        storageRef.child(path).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println("이미지 등록 테스트" + path);
                        Glide.with(getApplicationContext()).load(uri).into(image);
                    }
                });

        door = (ImageView) findViewById(R.id.control_door);
        humidifier = (ImageView)findViewById(R.id.control_humidifier);
        vent = (ImageView)findViewById(R.id.control_vent);
        light = (ImageView)findViewById(R.id.control_light);
        heat = (ImageView)findViewById(R.id.control_heat);
        device_name = (TextView)findViewById(R.id.control_device_name);
        undo = (Button)findViewById(R.id.control_undo);
        request = (Button)findViewById(R.id.control_request);

        device_name.setText(name);


        device_state.add(intent.getStringExtra("door"));
        device_state.add(intent.getStringExtra("humidifier"));
        device_state.add(intent.getStringExtra("vent"));
        device_state.add(intent.getStringExtra("light"));
        device_state.add(intent.getStringExtra("heat"));

        default_set(device_state);

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(door.isSelected()){
                    door.setSelected(false);
                    req_door = "CLOSE";
                }
                else {
                    door.setSelected(true);
                    req_door = "OPEN";
                }
            }
        });
        humidifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(humidifier.isSelected()){
                    humidifier.setSelected(false);
                    req_humidifier = "OFF";
                }
                else {
                    humidifier.setSelected(true);
                    req_humidifier = "ON";
                }
            }
        });
        vent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vent.isSelected()) {
                    vent.setSelected(false);
                    req_vent = "OFF";
                }
                else {
                    vent.setSelected(true);
                    req_vent = "ON";
                }
            }
        });
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(light.isSelected()) {
                    light.setSelected(false);
                    req_light = "OFF";
                }
                else {
                    light.setSelected(true);
                    req_light = "ON";
                }
            }
        });
        heat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(heat.isSelected()) {
                    heat.setSelected(false);
                    req_heat = "OFF";
                }
                else {
                    heat.setSelected(true);
                    req_heat = "ON";
                }
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                default_set(device_state);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 각 control 버튼 상태 읽어서 제어신호 전송
                Device_Data request = new Device_Data(name, connected, model_id, req_door, req_heat, req_humidifier, req_light, req_vent, 1);
                db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(model_id).collection("Control").document("Received")
                        .set(request);
                Toast.makeText(getApplicationContext(), "알림 : "+name+"에 제어 요청이 정상적으로 전송되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void default_set(List<String> device_states){
        int cnt = 0;
        for(String state : device_states) {
            if(cnt == 0){
                if(state.equals("ON")){
                    door.setSelected(true);
                    req_door = "OPEN";
                }
                else{
                    door.setSelected(false);
                    req_door = "CLOSE";
                }
            }
            else if(cnt == 1){
                if(state.equals("ON")){
                    humidifier.setSelected(true);
                    req_humidifier = "ON";
                }
                else{
                    humidifier.setSelected(false);
                    req_humidifier = "OFF";
                }
            }
            else if(cnt == 2){
                if(state.equals("ON")){
                    vent.setSelected(true);
                    req_vent = "ON";
                }
                else{
                    vent.setSelected(false);
                    req_vent = "OFF";
                }
            }
            else if(cnt == 3){
                if(state.equals("ON")){
                    light.setSelected(true);
                    req_light = "ON";
                }
                else{
                    light.setSelected(false);
                    req_light = "OFF";
                }
            }
            else if(cnt == 4){
                if(state.equals("ON")){
                    heat.setSelected(true);
                    req_heat = "ON";
                }
                else{
                    heat.setSelected(false);
                    req_heat = "OFF";
                }
            }
            cnt++;
        }
    }
}