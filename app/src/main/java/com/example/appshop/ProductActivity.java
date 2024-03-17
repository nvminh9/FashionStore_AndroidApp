package com.example.appshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
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
        ///******************** 1. Get Loại Sneakers  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Sneakers")){
            title.setText("Sneakers");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Sneakers").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        ///******************** 2. Get Loại T-shirts  *****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("T-shirts")){
            title.setText("T-shirts");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","T-shirts").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        ///******************** 3. Get Loại Pants  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Pants")){
            title.setText("Pants");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Pants").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        ///******************** 4. Get Loại Bags  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Bags")){
            title.setText("Bags");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Bags").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        ///******************** 5. Get Loại Knitwear  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Knitwear")){
            title.setText("Knitwear");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Knitwear").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        ///******************** 6. Get Loại Accessories  ****************************************************************************************************
        if(type != null && type.equalsIgnoreCase("Accessories")){
            title.setText("Accessories");
            firebaseFirestore.collection("SanPham").whereEqualTo("Loai","Accessories").addSnapshotListener(new EventListener<QuerySnapshot>() {
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