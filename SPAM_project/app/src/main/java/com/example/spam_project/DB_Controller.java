package com.example.spam_project;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class DB_Controller {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void User_Check(String User_Email) {
        DocumentReference doc = db.collection("User_info").document(User_Email);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("DB_Check", "User_info 존재함");
                            } else {
                                //등록된 이메일 정보가 아닌 경우 회원 이메일 추가
                                Sign_up(User_Email);
                                DB_Controller.User_Check(User_Email);
                            }
                        } else {
                            Log.d("DB_Check", "읽어오기 실패");
                        }
                    }
                });
    }

    public static List<Device_Data> Call_Device(String User_Email) {
        List<Device_Data> device = new ArrayList<>();
        device.add(new Device_Data(0));

        db.collection("User_info").document(User_Email).collection("User_Device")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DATA_Call", document.getId() + " -> " + document.getData());
                            }
                        }
                        else {
                            Log.d("DATA_Call", "Error");
                        }
                    }
                });
        // TODO : 읽어온 데이터 커스텀 객체 Device_Data에 매칭시키기
        device.add(new Device_Data(2));
        return device;
    }

    private static void Sign_up(String User_Email) {
        Map<String, Object> data = new HashMap<>();
        data.put("Device_Number", null);
        db.collection("User_info").document(User_Email).set(data);
        Log.d("DB_Check", "User_info 추가");
    }
}
