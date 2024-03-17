package com.example.appshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlacedOrderActivity extends AppCompatActivity {
    CircleImageView imguser;
    TextView totalpriceorder, textDate, textTime, nameuser, addressuser, emailuser;
    Button btnorderback;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userID;
    //Phần khai báo để sử dụng cho phần Random tạo mã đơn hàng
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final String ALL = alpha + alphaUpperCase + digits + specials;
    private static Random generator = new Random();

    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        initUI();
        initListener();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //get Total Price from CartAdapter
//        LocalBroadcastManager.getInstance(PlacedOrderActivity.this)
//                .registerReceiver(mMessageReceiver2, new IntentFilter("MyTotalAmount"));

        List<Cart> list = (ArrayList<Cart>) getIntent().getSerializableExtra("itemList");
        String totalorderprice = getIntent().getStringExtra("totalOrderPrice");

        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime()).toString();

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime()).toString();

        if(list != null && list.size() > 0){
            for(Cart model : list){
                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("HinhAnh",model.getHinhAnh());
                cartMap.put("TenSP",model.getTenSP());
                cartMap.put("Gia",model.getGia());
                cartMap.put("CartQuantity", model.getCartQuantity());
                cartMap.put("currentDate",model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());

                //Lưu đơn hàng có mã đơn hàng
                //****** Xử lý lưu lên Firebase Firestore  ********
                firebaseFirestore.collection("DonHang").document(firebaseAuth.getCurrentUser().getUid())
                        .collection(firebaseAuth.getCurrentUser().getUid() + "DH" + randomAlphaNumeric(8))
                        .add(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                totalpriceorder.setText(totalorderprice);
                                textDate.setText(saveCurrentDate);
                                textTime.setText(saveCurrentTime);
                                Toast.makeText(PlacedOrderActivity.this, "Your order has been confirmed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

        //****** Xử lý lấy data trên Firebase Firestore  ********
        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nameuser.setText(value.getString("fullName"));
                emailuser.setText(value.getString("Email"));
                addressuser.setText(value.getString("address"));
                Picasso.get().load(value.getString("imageURL")).into(imguser);
            }
        });

        // Xóa các sản phẩm trong cart sau khi đặt hàng thành công
        firebaseFirestore.collection("Cart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            String documentID = documentSnapshot.getId();
                            firebaseFirestore.collection("Cart").document(firebaseAuth.getCurrentUser().getUid())
                                    .collection("CurrentUser").document(documentID).delete();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void initUI(){
        btnorderback = (Button) findViewById(R.id.btnOrderBack);
        totalpriceorder = (TextView) findViewById(R.id.totalpriceorder);
        textDate = (TextView) findViewById(R.id.textView10);
        textTime = (TextView) findViewById(R.id.textView8);
        nameuser = (TextView) findViewById(R.id.nameuser);
        addressuser = (TextView) findViewById(R.id.addressuser);
        emailuser = (TextView) findViewById(R.id.emailuser);
        imguser = findViewById(R.id.imageView2);
    }
    private void initListener(){
        btnorderback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlacedOrderActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    //**************************** Các Hàm dùng để tạo mã đơn hàng ngẫu nhiên **************************
        //Hàm tạo số ngẫu nhiên
        public static int randomNumber(int min, int max) {
            return generator.nextInt((max - min) + 1) + min;
        }
        //Hàm tạo chuỗi ngẫu nhiên
        /**
         * Random string with a-zA-Z0-9, not included special characters
         */
        public String randomAlphaNumeric(int numberOfCharactor) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < numberOfCharactor; i++) {
                int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
                char ch = ALPHA_NUMERIC.charAt(number);
                sb.append(ch);
            }
            return sb.toString();
        }

}