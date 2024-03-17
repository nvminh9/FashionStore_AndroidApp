package com.example.appshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appshop.databinding.FragmentHomeBinding;
import com.example.appshop.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView rcvLoai, rcvTintuc;
    private ArrayList<Tintuc> mListTintuc;
    private ArrayList<Loai> mListLoai;
    //Khai báo Adapter cho Tin tức và model tin tức
    private TintucAdapter tintucAdapter;
    private Tintuc modelTintuc;
    //Khai báo Adapter cho Loại và model Loại
    private LoaiAdapter loaiAdapter;
    private Loai modelLoai;

    // Khai báo Firebase Cloud Firestore
    private FirebaseFirestore db;
    private CollectionReference tintucRef, loaiRef;
    private FragmentHomeBinding mFragmentHomeBinding;
    private View mView;
    private MainActivity mMainActivity = (MainActivity) getActivity();
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();
        initListener();

        //Xử lý cho RecyclerView Tin tức
        db = FirebaseFirestore.getInstance();
        mListTintuc = new ArrayList<Tintuc>();
        rcvTintuc.setHasFixedSize(true);
        GetTinTuc();

        //Xử lý cho RecyclerView Loại
        db = FirebaseFirestore.getInstance();
        mListLoai = new ArrayList<Loai>();
        rcvLoai.setHasFixedSize(true);
        GetLoai();

        return mView;
    }
    private void initUI(){
        rcvTintuc = (RecyclerView) mView.findViewById(R.id.rcv_tintuc);
        rcvLoai = (RecyclerView) mView.findViewById(R.id.rcvloaisp);
    }
    private void initListener(){

    }

    //*************** Xử lý lấy data tin tức thông thường *************
//    private void GetTinTuc() {
//        db.collection("Tintuc").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if(!queryDocumentSnapshots.isEmpty()){
//                    ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
//                    for(DocumentSnapshot d : list){
//                        //set modelTintuc thành Object
//                        modelTintuc = d.toObject(Tintuc.class);
//                        //add modelTintuc cho list Tin tuc
//                        mListTintuc.add(modelTintuc);
//                        tintucAdapter = new TintucAdapter(getContext(), mListTintuc);
//                        LinearLayoutManager layoutManagerTintuc = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
//                        rcvTintuc.setAdapter(tintucAdapter);
//                        rcvTintuc.setLayoutManager(layoutManagerTintuc);
//                        tintucAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }

    //********* Xử lý get data tin tức theo thời gian thực ************
    private void GetTinTuc() {
        db.collection("Tintuc").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(!value.isEmpty()){
                    ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) value.getDocuments();
                    if(mListTintuc != null){
                        mListTintuc.clear();
                    }
                    for(DocumentSnapshot d : list){
                        //set modelTintuc thành Object
                        modelTintuc = d.toObject(Tintuc.class);
                        //add modelTintuc cho list Tin tuc
                        mListTintuc.add(modelTintuc);
                        tintucAdapter = new TintucAdapter(getContext(), mListTintuc);
                        LinearLayoutManager layoutManagerTintuc = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                        rcvTintuc.setAdapter(tintucAdapter);
                        rcvTintuc.setLayoutManager(layoutManagerTintuc);
                        tintucAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

//    private void GetLoai() {
//        db.collection("Loai").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if(!queryDocumentSnapshots.isEmpty()){
//                    ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
//                    for(DocumentSnapshot d : list){
//                        modelLoai = d.toObject(Loai.class);
//                        mListLoai.add(modelLoai);
//                        loaiAdapter = new LoaiAdapter(getContext(), mListLoai);
//                        LinearLayoutManager layoutManagerLoai = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//                        rcvLoai.setAdapter(loaiAdapter);
//                        rcvLoai.setLayoutManager(layoutManagerLoai);
//                        loaiAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }

    //************** Xử lý get data Loại theo thời gian thực ***************
    private void GetLoai() {
        db.collection("Loai").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(!value.isEmpty()){
                    if(mListLoai != null){
                        mListLoai.clear();
                    }
                    ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) value.getDocuments();
                    for(DocumentSnapshot d : list){
                        modelLoai = d.toObject(Loai.class);
                        mListLoai.add(modelLoai);
                        loaiAdapter = new LoaiAdapter(getContext(), mListLoai);
                        LinearLayoutManager layoutManagerLoai = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        rcvLoai.setAdapter(loaiAdapter);
                        rcvLoai.setLayoutManager(layoutManagerLoai);
                        loaiAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}