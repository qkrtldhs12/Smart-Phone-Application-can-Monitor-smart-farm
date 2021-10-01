package com.example.spam_project.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.spam_project.Cell_Data;
import com.example.spam_project.R;
import com.example.spam_project.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home_fragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        List<Cell_Data> cell = new ArrayList<>();
        cell.add(new Cell_Data("이름1", "4", "4", "4", "4"));
        cell.add(new Cell_Data("이름2", "4", "4", "4", "4"));
        cell.add(new Cell_Data("이름3", "4", "4", "4", "4"));
        cell.add(new Cell_Data("이름4", "4", "4", "4", "4"));
        cell.add(new Cell_Data("이름5", "4", "4", "4", "4"));

        recyclerViewAdapter = new RecyclerViewAdapter(cell);
        recyclerView.setAdapter(recyclerViewAdapter);
        return rootView;
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
