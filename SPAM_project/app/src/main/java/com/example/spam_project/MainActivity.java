package com.example.spam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        getSupportFragmentManager().beginTransaction().add(R.id.main_content, new DeviceView_fragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_deviceview:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new DeviceView_fragment()).commit();
                        break;
                    case R.id.bottom_cellview:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new CellView_fragment()).commit();
                }
                return true;
            }
        });

    }

}