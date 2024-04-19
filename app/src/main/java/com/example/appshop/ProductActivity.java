package com.example.appshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appshop.models.SanPham;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    TextView title;
    ImageButton btnBack;
    FirebaseFirestore firebaseFirestore;

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<SanPham> sanPhamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //get instance
        firebaseFirestore = FirebaseFirestore.getInstance();

        initUI();
        initListener();

        //Hiện ds sp ra RecyclerView theo loại đã chọn
        String type = getIntent().getStringExtra("Loai");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2 ));
        sanPhamList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, sanPhamList);
        recyclerView.setAdapter(productAdapter);
        //*** Loại :
        ///******************** 1. Get Iphone  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Iphone")){
            title.setText("Iphone");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Iphone").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(sanPhamList != null){
                        sanPhamList.clear();
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        SanPham sanPham = doc.toObject(SanPham.class);
                        sanPhamList.add(sanPham);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            });
        }
        ///******************** 2. Get Samsung  *****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Samsung")){
            title.setText("Samsung");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Samsung").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(sanPhamList != null){
                        sanPhamList.clear();
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        SanPham sanPham = doc.toObject(SanPham.class);
                        sanPhamList.add(sanPham);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            });
        }
        ///******************** 3. Get Oppo ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Oppo")){
            title.setText("Oppo");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Oppo").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(sanPhamList != null){
                        sanPhamList.clear();
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        SanPham sanPham = doc.toObject(SanPham.class);
                        sanPhamList.add(sanPham);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            });
        }
        ///******************** 4. Get  Vivo ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Vivo")){
            title.setText("Vivo");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Vivo").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(sanPhamList != null){
                        sanPhamList.clear();
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        SanPham sanPham = doc.toObject(SanPham.class);
                        sanPhamList.add(sanPham);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            });
        }
        ///******************** 5. Get Realme  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Realme")){
            title.setText("Realme");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Realme").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(sanPhamList != null){
                        sanPhamList.clear();
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        SanPham sanPham = doc.toObject(SanPham.class);
                        sanPhamList.add(sanPham);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            });
        }
        ///******************** 6. Get Xiaomi  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Xiaomi")){
            title.setText("Xiaomi");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Xiaomi").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(sanPhamList != null){
                        sanPhamList.clear();
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        SanPham sanPham = doc.toObject(SanPham.class);
                        sanPhamList.add(sanPham);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            });
        }


    }

    private void initUI(){
        recyclerView = findViewById(R.id.rcvProduct);
        title = findViewById(R.id.textView3);
        btnBack = (ImageButton) findViewById(R.id.btnProfileBack);
    }
    private void initListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}