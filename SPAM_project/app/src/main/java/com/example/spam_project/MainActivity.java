package com.example.spam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.spam_project.navigation.CellView_fragment;
import com.example.spam_project.navigation.DeviceView_fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    public static List<String> My_Device = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String User_Email;
    public static int Cell_Counter;
    public static boolean Fragment_State;

    public interface Device_DataListener {
        void onSuccess(List<Device_Data> result);
    }
    public interface Cell_DataListener {
        void onSuccess(List<Cell_Data> result);
    }
    public interface DeviceCnt_DataListener {
        void onSuccess(List<String> result);
    }

    public static void Call_Device(FirebaseFirestore db, final Device_DataListener listener, String User_Email){
        db.collection("User_info").document(User_Email).collection("User_Device")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<Device_Data> device = new ArrayList<>();
                            device.add(new Device_Data(0));
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Device_Data result = document.toObject(Device_Data.class);
                                device.add(result);
                            }
                            device.add(new Device_Data(2));
                            listener.onSuccess(device);
                        }
                        else {
                            Log.d("DATA_Call", "Error");
                        }
                    }
                });
    }

    public static void Call_Cell(FirebaseFirestore db, final Cell_DataListener listener, String User_Email) {
        My_Device.clear();
        Call_DeviceCnt(db, new DeviceCnt_DataListener() {
            @Override
            public void onSuccess(List<String> result) {
                My_Device = result;
                Cell_Counter = result.size();
                List<Cell_Data> cell = new ArrayList<>();
                cell.add(new Cell_Data(0));
                for(String Device_Name : My_Device) {
                    db.collection("User_info").document(User_Email).collection("User_Device").document(Device_Name).collection("Cell_Data")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Cell_Counter--;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Cell_Data result = document.toObject(Cell_Data.class);
                                            cell.add(result);
                                        }
                                        if(Cell_Counter == 0) {
                                            cell.add(new Cell_Data(2));
                                            listener.onSuccess(cell);
                                        }
                                    } else {
                                        Log.d("DATA_Call", "Error");
                                    }
                                }
                            });
                }
            }
        }, User_Email);
    }

    public static void Call_DeviceCnt(FirebaseFirestore db, final DeviceCnt_DataListener listener, String User_Email) {
        db.collection("User_info").document(User_Email).collection("User_Device")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                My_Device.add(document.getId());
                            }
                        }
                        else{
                            Log.d("DATA_Call", "Error");
                        }
                        listener.onSuccess(My_Device);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        User_Email = intent.getStringExtra("User_Email");

        bottomNavigationView = findViewById(R.id.bottom_nav);

        Call_Device(db, new Device_DataListener() {
            @Override
            public void onSuccess(List<Device_Data> result) {
                DeviceView_fragment deviceview = new DeviceView_fragment();
                deviceview.device = result;
                getSupportFragmentManager().beginTransaction().add(R.id.main_content, deviceview).commit();
                Fragment_State = true;
            }
        }, User_Email);

        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_deviceview:
                        Call_Device(db, new Device_DataListener() {
                            @Override
                            public void onSuccess(List<Device_Data> result) {
                                DeviceView_fragment deviceview = new DeviceView_fragment();
                                deviceview.device = result;
                                getSupportFragmentManager().beginTransaction().add(R.id.main_content, deviceview).commit();
                            }
                        }, User_Email);
                        Fragment_State = true;
                        break;
                    case R.id.bottom_cellview:
                        Call_Cell(db, new Cell_DataListener() {
                            @Override
                            public void onSuccess(List<Cell_Data> result) {
                                CellView_fragment cellview = new CellView_fragment();
                                cellview.cell = result;
                                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, cellview).commit();
                            }
                        }, User_Email);
                        Fragment_State = false;
                        break;
                    case R.id.bottom_reload:
                        if(Fragment_State){
                            Call_Device(db, new Device_DataListener() {
                                @Override
                                public void onSuccess(List<Device_Data> result) {
                                    DeviceView_fragment deviceview = new DeviceView_fragment();
                                    deviceview.device = result;
                                    getSupportFragmentManager().beginTransaction().add(R.id.main_content, deviceview).commit();
                                }
                            }, User_Email);
                        }
                        else{
                            Call_Cell(db, new Cell_DataListener() {
                                @Override
                                public void onSuccess(List<Cell_Data> result) {
                                    CellView_fragment cellview = new CellView_fragment();
                                    cellview.cell = result;
                                    getSupportFragmentManager().beginTransaction().replace(R.id.main_content, cellview).commit();
                                }
                            }, User_Email);
                        }
                }
                return true;
            }
        });

    }


}