package com.example.spam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CellControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cell_control);
        Intent intent = getIntent();
        System.out.println("model_id : " + intent.getStringExtra("model_id"));
        System.out.println("name : " + intent.getStringExtra("name"));
        System.out.println("soil : " + intent.getStringExtra("soil"));
        System.out.println("temp : " + intent.getStringExtra("temp"));
        System.out.println("humi : " + intent.getStringExtra("humi"));

        System.out.println("name_list : " + intent.getStringArrayListExtra("name_list"));

        /*intent.putStringArrayListExtra("name_list", My_Cell);
        intent.putExtra("name", cellViewAdapter.getItem(position).getName());
        intent.putExtra("model_id", cellViewAdapter.getItem(position).getModel_id());
        intent.putExtra("humi", cellViewAdapter.getItem(position).getHumi());
        intent.putExtra("soil", cellViewAdapter.getItem(position).getSoil());
        intent.putExtra("temp", cellViewAdapter.getItem(position).getTemp());*/
    }
}