package com.example.appshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firestore.v1.WriteResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyCartViewHolder>{

    Context context;
    List<Cart> cartModelList;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userID;
    long totalquantity = 1;
    long totalBill;
    long totalprice;

    public CartAdapter(Context context, List<Cart> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_incart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();


        Picasso.get().load(cartModelList.get(position).getHinhAnh()).into(holder.HinhAnh);
        holder.TenSP.setText(cartModelList.get(position).getTenSP());
        holder.Gia.setText("Price : " + cartModelList.get(position).getGia() + "$");
        holder.currentDate.setText(cartModelList.get(position).getCurrentDate());
        holder.currentTime.setText(cartModelList.get(position).getCurrentTime());
        holder.txtquantity.setText(String.valueOf(cartModelList.get(position).getCartQuantity()));

        //phần số lượng
        //nút tăng số lượng
        holder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalquantity < 10){
                    if( totalquantity == Long.valueOf(holder.txtquantity.getText().toString()) ){
                        totalquantity++;
                        holder.txtquantity.setText(String.valueOf(totalquantity));
                        DocumentReference docRef = firebaseFirestore.collection("Cart").document(userID)
                                .collection("CurrentUser").document(cartModelList.get(position).getTenSP());
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("CartQuantity", Long.parseLong((String) holder.txtquantity.getText()) );
                        docRef.update(updatedData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle any errors
                                        Toast.makeText(context, "Error Update data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else {
                        totalquantity = Long.valueOf(holder.txtquantity.getText().toString());
                        totalquantity++;
                        holder.txtquantity.setText(String.valueOf(totalquantity));
                        DocumentReference docRef = firebaseFirestore.collection("Cart").document(userID)
                                .collection("CurrentUser").document(cartModelList.get(position).getTenSP());
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("CartQuantity", Long.parseLong((String) holder.txtquantity.getText()));
                        docRef.update(updatedData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Update successful
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle any errors
                                        Toast.makeText(context, "Error Update data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });
        //nút giảm số lượng
        holder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalquantity > 1){
                    if( totalquantity == Long.valueOf(holder.txtquantity.getText().toString()) ){
                        totalquantity--;
                        holder.txtquantity.setText(String.valueOf(totalquantity));
                        DocumentReference docRef = firebaseFirestore.collection("Cart").document(userID)
                                .collection("CurrentUser").document(cartModelList.get(position).getTenSP());
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("CartQuantity", Long.parseLong((String) holder.txtquantity.getText()));
                        docRef.update(updatedData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle any errors
                                        Toast.makeText(context, "Error Update data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else {
                        totalquantity = Long.valueOf(holder.txtquantity.getText().toString());
                        totalquantity--;
                        holder.txtquantity.setText(String.valueOf(totalquantity));
                        DocumentReference docRef = firebaseFirestore.collection("Cart").document(userID)
                                .collection("CurrentUser").document(cartModelList.get(position).getTenSP());
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("CartQuantity", Long.parseLong((String) holder.txtquantity.getText()));
                        docRef.update(updatedData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Update successful
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle any errors
                                        Toast.makeText(context, "Error Update data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });

        //Phần xóa sản phẩm trong giỏ hàng khi bấm nút Delete
        holder.btnCartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( cartModelList.get(position).getTenSP() != null ){
                    firebaseFirestore.collection("Cart").document(userID)
                            .collection("CurrentUser").document(cartModelList.get(position).getTenSP()).delete();
                    Toast.makeText(context, "Delete product successfuly", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Error, can't delete product from your cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder{
        TextView TenSP, Gia, currentDate, currentTime;
        ImageView HinhAnh;
        TextView txtquantity;
        Button btnminus, btnplus, btnCartDelete;

        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);

            //ánh xạ view
            HinhAnh = itemView.findViewById(R.id.imgitemcart);
            TenSP = itemView.findViewById(R.id.namecartitemsp);
            Gia = itemView.findViewById(R.id.pricecartitemsp);
            currentDate = itemView.findViewById(R.id.currentDate);
            currentTime = itemView.findViewById(R.id.currentTime);
            //phần số lượng
            txtquantity = itemView.findViewById(R.id.txtquantity);
            btnminus = itemView.findViewById(R.id.btnminus);
            btnplus = itemView.findViewById(R.id.btnplus);
            //phần xóa sản phẩm trong giỏ hàng
            btnCartDelete = itemView.findViewById(R.id.btnCartDelete);
        }
    }

}
