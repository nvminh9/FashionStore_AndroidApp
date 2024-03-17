package com.example.appshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailProductActivity extends AppCompatActivity {
    ImageView imgItem;
    TextView txtItemName, txtItemPrice, detailquantity, txtItemDescrip;
    ImageButton btnBack, btnfav;
    Button btnAddCart, btnSizeChart, btnOrder;
    SanPham sanPham = null;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof SanPham){
            sanPham = (SanPham) object;
        }

        initUI();
        initListener();

        if (sanPham != null){
            Glide.with(getApplicationContext()).load(sanPham.getHinhAnh()).into(imgItem);
            txtItemName.setText(sanPham.getTenSP());
            txtItemPrice.setText("Price : " + sanPham.getGia() + "$");
            detailquantity.setText("Quantity : " + sanPham.getQuantity());
            txtItemDescrip.setText(sanPham.getMoTa());
        }
    }

    private void initUI(){
        btnBack = findViewById(R.id.btnDetailBack);
        btnfav = findViewById(R.id.favbtn);
        btnAddCart = findViewById(R.id.btnAddCart);
//        btnOrder = findViewById(R.id.btnOrder);
//        btnSizeChart = findViewById(R.id.btnSizeChart);
        txtItemName = findViewById(R.id.txtitemname);
        txtItemPrice = findViewById(R.id.txtitemprice);
        txtItemDescrip = findViewById(R.id.txtitemdescrip);
        imgItem = findViewById(R.id.imgdetail);
        detailquantity = findViewById(R.id.detailquantity);
    }
    private void initListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart(){
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("HinhAnh",sanPham.getHinhAnh());
        cartMap.put("TenSP",sanPham.getTenSP());
        cartMap.put("Gia",sanPham.getGia());
        cartMap.put("CartQuantity", 1);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);

        //****** Xử lý lưu lên Firebase Firestore  ********
        firebaseFirestore.collection("Cart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("CurrentUser").document(sanPham.getTenSP()).set(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Added to cart successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: Add to cart fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}