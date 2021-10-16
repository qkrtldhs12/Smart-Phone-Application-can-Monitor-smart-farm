package com.example.spam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add_DeviceActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        EditText edit_model_id = (EditText)findViewById(R.id.edit_device_model_id);
        EditText edit_name = (EditText)findViewById(R.id.edit_device_name);
        Button edit_btn = (Button)findViewById(R.id.edit_device_btn);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User_Email 컬렉션에서 전체 모델명 검사 후 입력 모델명이 존재하는지 확인
                // 입력한 모델명이 이미 이메일 등록된 모델인지 확인
                db.collection("User_Email").whereIn("Email", Arrays.asList("no_registered")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<String> model_list = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                model_list.add(document.getId());
                            }
                            if(model_list.contains(edit_model_id.getText().toString())){
                                // 사용자가 입력한 모델명이 등록 가능한 상태
                                Map<String, Object> reg = new HashMap<>();
                                reg.put("Email", MainActivity.User_Email);
                                db.collection("User_Email").document(edit_model_id.getText().toString()).set(reg);
                                Device_Data input = new Device_Data(edit_name.getText().toString(), "0", edit_model_id.getText().toString(), "CLOSE", "OFF", "OFF", "OFF", "OFF", 1);
                                db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(edit_model_id.getText().toString()).set(input);
                                MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "오류 : 등록할 수 있는 모델명이 아닙니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "오류 : 현재 등록할 수 있는 모델이 없습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
                /*if (!MainActivity.My_Device.contains(edit_model_id.getText().toString())) {
                    Device_Data input = new Device_Data(edit_name.getText().toString(), "0", edit_model_id.getText().toString(), "CLOSE", "OFF", "OFF", "OFF", "OFF", 1);
                    db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(edit_model_id.getText().toString()).set(input);
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "오류 : 이미 등록된 모델명입니다.", Toast.LENGTH_SHORT).show();
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                    finish();
                }*/
            }
        });
    }
}