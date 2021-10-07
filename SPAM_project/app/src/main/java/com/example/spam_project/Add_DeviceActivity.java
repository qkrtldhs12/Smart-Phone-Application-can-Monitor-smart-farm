package com.example.spam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

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
                // 이미 등록된 디바이스는 추가하지 않도록 수정해야 함
                if (!MainActivity.My_Device.contains(edit_model_id.getText().toString())) {
                    Device_Data input = new Device_Data(edit_name.getText().toString(), "0", edit_model_id.getText().toString(), "CLOSE", "OFF", "OFF", "OFF", "OFF", 1);
                    db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(edit_model_id.getText().toString()).set(input);
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "오류 : 이미 등록된 모델명입니다.", Toast.LENGTH_SHORT).show();
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                    finish();
                }
            }
        });
    }
}