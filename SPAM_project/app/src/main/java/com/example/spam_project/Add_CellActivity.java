package com.example.spam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Add_CellActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cell);

        // 셀을 추가할 수 있는 모델명을 스피너로 출력
        Spinner spinner = findViewById(R.id.edit_device_model_id);
        EditText edit_name = (EditText)findViewById(R.id.edit_cell_name);
        Button edit_btn = (Button)findViewById(R.id.edit_cell_btn);
        db.collection("User_Email").whereIn("Email", Arrays.asList(MainActivity.User_Email)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    // 유저 이메일로 등록된 모델명 조회
                    List<String> my_device = new ArrayList<String>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        my_device.add(document.getId());
                    }
                    if(my_device.size()==0){
                        Toast.makeText(getApplicationContext(), "오류 : 화분을 등록할 모델이 없습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    my_device.add(" 연결된 모델명");
                    String[] items = my_device.toArray(new String[my_device.size()]);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, items){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){
                            View v = super.getView(position, convertView, parent);
                            if(position == getCount()){
                                ((TextView)v.findViewById(android.R.id.text1)).setText("");
                                ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                            }
                            return v;
                        }
                        @Override
                        public int getCount(){
                            return super.getCount()-1;
                        }
                    };
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(adapter.getCount());
                }
                else{
                    Toast.makeText(getApplicationContext(), "오류 : 화분을 등록할 모델이 없습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cell_Data input = new Cell_Data(edit_name.getText().toString(), spinner.getSelectedItem().toString(), "0", "0", "0", 1);
                db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(spinner.getSelectedItem().toString()).collection("Cell_Data").document(edit_name.getText().toString())
                        .set(input);
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_reload);
                finish();
            }
        });
    }
}