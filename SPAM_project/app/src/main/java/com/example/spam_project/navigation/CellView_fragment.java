package com.example.spam_project.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spam_project.Cell_Data;
import com.example.spam_project.DB_Controller;
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

        Bundle bundle = getArguments();
        String User_Email = bundle.getString("User_Email");

        List<Cell_Data> cell = new ArrayList<>();
        cell.add(new Cell_Data(0));
        //파이어베이스 셀 데이터 읽어오기
        DB_Controller.Call_Device(User_Email);
        cell.add(new Cell_Data(2));

        cellViewAdapter = new CellViewAdapter(cell);
        cellViewAdapter.setOnItemClickListener(new CellViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Intent intent = new Intent(getActivity(), TestActivity.class);
                // 프래그먼트상에선 context가 존재하지 않기 때문에 getActivty()를 통해 실행시킴
                //startActivity(intent);
            }
        });
        recyclerView.setAdapter(cellViewAdapter);
        return rootView;
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
