package com.example.spam_project;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
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
                                Log.d("DB_Check 테스트", document.getId());
                            } else {
                                Sign_up(User_Email);
                            }
                        } else {
                            Log.d("DB_Check 에러", "읽어오기 실패");
                        }
                    }
                });
    }

    private static void Sign_up(String User_Email) {
        Map<String, Object> data = new HashMap<>();
        data.put("Device_Number", null);
        db.collection("User_info").document(User_Email).set(data);
        Log.d("DB_Check.Sign_up", "회원추가");
    }
}
