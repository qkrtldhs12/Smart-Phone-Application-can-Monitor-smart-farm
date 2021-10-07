package com.example.spam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class Add_CellActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cell);

        EditText edit_model_id = (EditText)findViewById(R.id.edit_device_model_id);
        EditText edit_name = (EditText)findViewById(R.id.edit_cell_name);
        Button edit_btn = (Button)findViewById(R.id.edit_cell_btn);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cell_Data input = new Cell_Data(edit_name.getText().toString(), edit_model_id.getText().toString(), "0", "0", "0", 1);
                if (!MainActivity.My_Device.contains(edit_model_id.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "오류 : 계정에 등록되지 않은 모델명입니다.", Toast.LENGTH_SHORT).show();
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                    finish();
                }
                else {

                    db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(edit_model_id.getText().toString()).collection("Cell_Data").document(edit_name.getText().toString())
                            .set(input);
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                    finish();
                }
            }
        });
    }
}