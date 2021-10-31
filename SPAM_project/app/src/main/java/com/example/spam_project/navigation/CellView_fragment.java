package com.example.spam_project.navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spam_project.Add_CellActivity;
import com.example.spam_project.Cell_Data;
import com.example.spam_project.MainActivity;
import com.example.spam_project.R;
import com.example.spam_project.CellViewAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CellView_fragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CellViewAdapter cellViewAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ImageView current_image;
    String model_id, name;
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
                                String model_id = cell.get(position).getModel_id();
                                cell.remove(position);
                                cellViewAdapter.notifyItemRemoved(position);
                                cellViewAdapter.notifyItemRangeChanged(position, cell.size());
                                for(String device : MainActivity.My_Device) {
                                    db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(device).collection("Cell_Data").document(delete)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    String path = "image/" + model_id + "/" + delete + ".png";
                                                    storageRef.child(path).delete();
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

        //짧은 클릭 -> 셀 추가 버튼
        cellViewAdapter.setOnItemClickListener(new CellViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(cell.size()-1 == position) {
                    Intent intent = new Intent(getActivity(), Add_CellActivity.class);
                    startActivity(intent);
                }
            }
        });

        //이미지, 버튼 클릭
        cellViewAdapter.setOnImageClickListener(new CellViewAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(View view, int position) {
                current_image = (ImageView) view.findViewById(R.id.item_image);
                model_id = cellViewAdapter.getItem(position).getModel_id();
                name = cellViewAdapter.getItem(position).getName();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }

            @Override
            public void onBtnClick(View view, int position) {
                System.out.println("이미지 버튼 클릭");
                Map<String, Object> req_water = new HashMap<>();
                req_water.put("Water", "ON");
                db.collection("User_info").document(MainActivity.User_Email).collection("User_Device").document(cellViewAdapter.getItem(position).getModel_id())
                        .collection("Cell_Data").document(cellViewAdapter.getItem(position).getName()).collection("Control")
                        .document("Received").set(req_water);
                Toast.makeText(getActivity(), "알림 : "+cellViewAdapter.getItem(position).getName()+"에 물주기 요청이 정상적으로 전송되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(cellViewAdapter);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getData();
                    Glide.with(getActivity()).load(uri).into(current_image);
                    System.out.println("모델 아이디"+model_id);
                    System.out.println("네임"+name);
                    String path = "image/" + model_id + "/" + name + ".png";
                    UploadTask uploadTask = storageRef.child(path).putFile(uri);
                }
                catch (Exception e) {

                }
            }
        }
        else if(resultCode == RESULT_CANCELED) {
            //사진 선택 취소
        }
    }

    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
