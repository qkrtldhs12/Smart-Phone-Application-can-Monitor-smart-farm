package com.example.spam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.google.type.DateTime;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference docRef = db.collection("data").document("cell_1").collection("time");
    private final static int lgn = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView test_view = (TextView)findViewById(R.id.test_view);
        Button call_btn = (Button)findViewById(R.id.call_btn);
        Button login_btn = (Button)findViewById(R.id.login_button);
        call_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                docRef.orderBy("time", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot collection = task.getResult();
                            for(DocumentSnapshot ds : collection){
                                String humi = ds.get("humi").toString();
                                String lumi = ds.get("lumi").toString();
                                String soil = ds.get("soil").toString();
                                String temp = ds.get("temp").toString();
                                Timestamp time = ds.getTimestamp("time");
                                Log.d("성공:", time.toDate().toString());
                                String text = "humi: "+ humi + "\nlumi: " + lumi + "\nsoil: " + soil +"\ntemp: " + temp + "\ntime: "+time.toDate();
                                test_view.setText(text);
                                Log.d("성공:", "아아아아");
                            }
                        }
                        else {
                            Log.d("실패:", "No such data");
                        }
                    }
                });
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //Intent에 데이터 입력 : intent.putExtra("데이터 이름", "데이터")
                //Intent에서 데이터 출력 : intent.getExtras().getString("데이터이름)
                // .getString 부분은 자료형에 따라 getInt 등 가능
                //Intent 수신은 해당 액티비티에서 Intent를 선언한 후 getIntent()로 가져올 수 있음
                startActivityForResult(intent, lgn);
            }
        });

    }


}