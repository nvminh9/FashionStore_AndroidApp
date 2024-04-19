package com.example.appshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appshop.models.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    TextView titlecart, totalprice;
    Button Order;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userID;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<Cart> cartModelList;
    View view;
    public long totalorder = 0;

    public CartFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        initUI();
        initListener();

        //get Total Price from CartAdapter
//        LocalBroadcastManager.getInstance(getContext())
//                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        //***********  Xử lý xuất các sp có trong giỏ hàng bằng userID theo realtime  ***********************************************************************
        recyclerView = view.findViewById(R.id.rcv_item_inCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);
        final DocumentReference docRef = firebaseFirestore.collection("Cart").document(firebaseAuth.getCurrentUser().getUid());
        docRef.collection("CurrentUser").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(cartModelList != null){
                    cartModelList.clear();
                }
                for (QueryDocumentSnapshot doc : value) {
                    Cart cartModel = doc.toObject(Cart.class);
                    cartModelList.add(cartModel);
                }
                cartAdapter.notifyDataSetChanged();
            }
        });

        //Xử lý total order
        firebaseFirestore.collection("Cart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("CurrentUser").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(totalorder != 0){
                            totalorder = 0;
                        }
                        for(DocumentSnapshot documentSnapshot : value){
                            totalorder = totalorder + (documentSnapshot.getLong("Gia") * documentSnapshot.getLong("CartQuantity") );
                        }
                        totalprice.setText("Total : " + String.valueOf(totalorder) + "$");
                    }
                });


        return view;
    }

    private void initUI(){
        titlecart = view.findViewById(R.id.titlecart);
        totalprice = view.findViewById(R.id.totalprice);
        Order = view.findViewById(R.id.btnCartOrder);
    }
    private void initListener(){
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( totalorder != 0 ){
                    Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), PlacedOrderActivity.class);
                    i.putExtra("itemList", (Serializable) cartModelList);
                    i.putExtra("totalOrderPrice", totalprice.getText());
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(), "There are no products in the cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}