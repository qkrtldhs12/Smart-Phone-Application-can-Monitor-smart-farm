package com.example.spam_project.navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spam_project.Add_CellActivity;
import com.example.spam_project.Cell_Data;
import com.example.spam_project.MainActivity;
import com.example.spam_project.R;
import com.example.spam_project.CellViewAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CellView_fragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CellViewAdapter cellViewAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static List<Cell_Data> cell;

    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cellview, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        cellViewAdapter = new CellViewAdapter(cell);

        //긴 클릭
        cellViewAdapter.setOnItemLongClickListener(new CellViewAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("연결된 환경 제거")
                        .setMessage("삭제하시겠습니까?")
                        .setIcon(R.drawable.ic_alarm)
                        .setNegativeButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Item 삭제
                                String delete = cell.get(position).getName();
                                cell.remove(position);
                                cellViewAdapter.notifyItemRemoved(position);
                                cellViewAdapter.notifyItemRangeChanged(position, cell.size());
                                for(String device : MainActivity.My_Device) {
                                    db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(device).collection("Cell_Data").document(delete)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    //DB 삭제 성공
                                                }
                                            });
                                }

                            }
                        })
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //삭제 취소
                            }
                        })
                        .show();
            }
        });

        //짧은 클릭
        cellViewAdapter.setOnItemClickListener(new CellViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(cell.size()-1 == position) {
                    Intent intent = new Intent(getActivity(), Add_CellActivity.class);
                    startActivity(intent);
                }
                else{
                    Map<String, Object> req_water = new HashMap<>();
                    req_water.put("물줘", "물줘");
                    db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(cellViewAdapter.getItem(position).getModel_id())
                            .collection("Cell_Data").document(cellViewAdapter.getItem(position).getName()).collection("Control")
                            .document("Received").set(req_water);
                    Toast.makeText(getActivity(), "알림 : "+cellViewAdapter.getItem(position).getName()+"에 물주기 요청이 정상적으로 전송되었습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        recyclerView.setAdapter(cellViewAdapter);
        return rootView;
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
