package com.example.appshop;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appshop.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.C;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private Button btnEditProfile, btnLogout, btnChangePass;
    private TextView nameprofile, emailprofile, addressprofile, useridprofile;
    private CircleImageView imgprofile;
    private FragmentProfileBinding mFragmentProfileBinding;
    private View mView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String CurrentUserID;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile,container,false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserID = firebaseAuth.getCurrentUser().getUid();

        initUI();
        initListener();

        //Xử lý update edit profile
        setUserInformation();

        return mView;
    }

    private void initUI(){
        btnEditProfile = (Button) mView.findViewById(R.id.btnEditProfile);
        btnLogout = (Button) mView.findViewById(R.id.btnLogout);
        btnChangePass = (Button) mView.findViewById(R.id.btnChangePass);

        nameprofile = (TextView) mView.findViewById(R.id.nameprofile);
        emailprofile = (TextView) mView.findViewById(R.id.emailprofile);
        addressprofile = (TextView) mView.findViewById(R.id.addressprofile);
        useridprofile = (TextView) mView.findViewById(R.id.useridprofile);
        imgprofile = (CircleImageView) mView.findViewById(R.id.imgprofile);
    }
    private void initListener(){
        //nút Edit profile
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditActivity();
            }
        });
        //nút đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
        //Nút đổi / reset mật khẩu
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void toEditActivity() {
        Intent i = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(i);
    }

    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getContext(),"Logout Successful!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), SplashActivity.class);
        startActivity(i);
    }

    private void setUserInformation(){
        final DocumentReference documentReference;
        documentReference = firebaseFirestore.collection("Users").document(CurrentUserID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                Glide.with(getActivity()).load(documentSnapshot.getString("imageURL")).error(R.drawable.avatardefault).into(imgprofile);
                if(documentSnapshot.getString("imageURL") != null){
                    Picasso.get().load(documentSnapshot.getString("imageURL")).into(imgprofile);
                }else {
                    imgprofile.setImageResource(R.drawable.avatardefault);
                }
                nameprofile.setText("Name : " + documentSnapshot.getString("fullName"));
                emailprofile.setText("E-mail : " + documentSnapshot.getString("Email"));
                addressprofile.setText("Address : " + documentSnapshot.getString("address"));
                useridprofile.setText("UserID : " + CurrentUserID);
            }
        });

    }
}