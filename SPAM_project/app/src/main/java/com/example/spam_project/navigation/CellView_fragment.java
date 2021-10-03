package com.example.spam_project.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spam_project.Cell_Data;
import com.example.spam_project.R;
import com.example.spam_project.CellViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CellView_fragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CellViewAdapter cellViewAdapter;

    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cellview, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        List<Cell_Data> cell = new ArrayList<>();
        cell.add(new Cell_Data(0));
        cell.add(new Cell_Data("이름1", "4", "4", "4", "4", 1));
        cell.add(new Cell_Data("이름2", "4", "4", "4", "4", 1));
        cell.add(new Cell_Data("이름3", "4", "4", "4", "4", 1));
        cell.add(new Cell_Data("이름4", "4", "4", "4", "4", 1));
        cell.add(new Cell_Data("이름5", "4", "4", "4", "4", 1));

        cellViewAdapter = new CellViewAdapter(cell);
        recyclerView.setAdapter(cellViewAdapter);
        return rootView;
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
