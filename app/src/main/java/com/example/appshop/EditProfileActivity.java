package com.example.appshop;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appshop.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.BitSet;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtFullName, edtAddress;
    private Button btnConfirm;
    private ImageButton btnBack;
    private CircleImageView selectCircleImageView;
    private Uri imgUri;
    private Bitmap imgBitmap;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference mStorageReference;
    private String imageURL;
    private FirebaseAuth firebaseAuth;
    private String CurrentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //create instance
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mStorageReference = firebaseStorage.getReference();
        //get UserID
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserId = firebaseAuth.getCurrentUser().getUid();

        initUI();
        initListener();

        //Xử lý ánh xạ dữ liệu profile hiện tại vào activity EditProfile
        setUserInformation();

    }
    private void initUI(){
        selectCircleImageView = findViewById(R.id.imgprofile);
        edtFullName = findViewById(R.id.edtFullName);
        edtAddress = findViewById(R.id.edtAddress);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);
    }
    private void initListener(){
        selectCircleImageView.setOnClickListener(v -> mGetContent.launch("image/*"));
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
                UploadUserInformation();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void CheckStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getApplicationContext(),
                    READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{READ_EXTERNAL_STORAGE},1);
            }else {
//                view -> launcher.launch("image/*");
            }
        }else {
//            //method để chọn ảnh từ gallery
//            PickImageFromGallery();
        }
    }

    //method để chọn ảnh từ gallery
    private void PickImageFromGallery(){
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        //launcher
//        launcher.launch(i);
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    selectCircleImageView.setImageURI(uri);
                    imgUri = uri;
                }
            });
    //upload image
    //***************************************************************************************************************************************
    //******************  method to upload Image to FirebaseStorage  ************************************************************************
    private void UploadImage(){
        //check Uri
        if(imgUri != null){
            //storage firebase instance
            final StorageReference myRef = mStorageReference.child("photo/" + imgUri.getLastPathSegment());
            myRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //store in String with dowloadUrl
                    myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri != null){
                                imageURL = uri.toString();
                                UploadUserInformation();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //method to upload các information khác
    private void UploadUserInformation(){
        String strFullName = edtFullName.getText().toString().trim();
        String strAddress = edtAddress.getText().toString().trim();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(CurrentUserId);
        Users users = new Users(strFullName,strAddress,imageURL,CurrentUserId);
        documentReference.set(users, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //sau khi successfull lưu các thông tin như fullname, address, imgURL thì check thêm lần nữa để lưu userID
                    Toast.makeText(getApplicationContext(), "Your changes have been confirmed", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserInformation(){
        final DocumentReference documentReference;
        documentReference = firebaseFirestore.collection("Users").document(CurrentUserId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                Glide.with(EditProfileActivity.this).load(documentSnapshot.getString("imageURL")).error(R.drawable.avatardefault).into(selectCircleImageView);
                if(documentSnapshot.getString("imageURL") != null && documentSnapshot.getString("imageURL") != "" ){
                    Picasso.get().load(documentSnapshot.getString("imageURL")).into(selectCircleImageView);
                }else {
                    selectCircleImageView.setImageResource(R.drawable.avatardefault);
                }
                if(documentSnapshot.getString("fullName") != null && documentSnapshot.getString("fullName") != ""){
                    edtFullName.setText(documentSnapshot.getString("fullName"));
                }else {
                    edtFullName.setHint("FullName");
                }
                if(documentSnapshot.getString("address") != null && documentSnapshot.getString("address") != ""){
                    edtAddress.setText(documentSnapshot.getString("address"));
                }else {
                    edtAddress.setHint("Address");
                }

            }
        });
    }

}