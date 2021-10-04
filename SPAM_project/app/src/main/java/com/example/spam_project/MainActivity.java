package com.example.spam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.spam_project.navigation.AddDevice_fragment;
import com.example.spam_project.navigation.CellView_fragment;
import com.example.spam_project.navigation.DeviceView_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        Intent intent = getIntent();
        String User_Email = intent.getStringExtra("User_Email");
        Bundle bundle = new Bundle();
        bundle.putString("User_Email", User_Email);

        DeviceView_fragment deviceview = new DeviceView_fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, deviceview).commit();
        deviceview.setArguments(bundle);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_deviceview:
                        DeviceView_fragment deviceview = new DeviceView_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, deviceview).commit();
                        deviceview.setArguments(bundle);
                        break;
                    case R.id.bottom_cellview:
                        CellView_fragment cellview = new CellView_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, cellview).commit();
                        cellview.setArguments(bundle);
                }
                return true;
            }
        });

    }

}