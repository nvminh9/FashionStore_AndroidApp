package com.example.appshop;

import static android.content.ContentValues.TAG;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText txtName, txtEmail, txtPassword, txtRePassword;
    private Button btnNext, btnLogin;
    private ImageButton btnBack;
    //Khai báo Firestore để lưu thông tin đăng ký
    private FirebaseFirestore firebaseFirestore;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();
        initListener();
    }
    private void initUI(){
        //các ô nhận giá trị
        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRePassword = (EditText) findViewById(R.id.txtRePassword);
        //nút chức năng
        btnNext = (Button) findViewById(R.id.btnNext);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }
    private void initListener(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLogin();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
//                SignupActivity.super.onBackPressed();
            }
        });
    }

    private void register(){
        String strName = txtName.getText().toString().trim();
        String strEmail = txtEmail.getText().toString().trim();
        String strPassword = txtPassword.getText().toString().trim();
        String strRePassword = txtRePassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if(TextUtils.isEmpty(strName) && TextUtils.isEmpty(strEmail) && TextUtils.isEmpty(strPassword) && TextUtils.isEmpty(strRePassword)){
            Toast.makeText(this,"Please fill in all information", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(strName)){
            Toast.makeText(this,"Please enter Name !", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(strEmail)){
            Toast.makeText(this,"Please enter Email !", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(strPassword)){
            Toast.makeText(this,"Please enter Password !", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(strRePassword)){
            Toast.makeText(this,"Please enter RePassword !", Toast.LENGTH_SHORT).show();
            return;
        }else if(strPassword.equals(strRePassword)){
            auth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Đăng ký thành công , sau đó chuyển qua activity đăng nhập
                                Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                                userID = auth.getCurrentUser().getUid();
                                DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("FullName", strName);
                                user.put("Email", strEmail);
                                user.put("Password", strPassword);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignupActivity.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(i);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                // nếu đăng ký không thành công sẽ có thông báo
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Sign Up Fail!", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }
                    });
        }else {
            Toast.makeText(this, "Wrong password re-entered !", Toast.LENGTH_SHORT).show();
        }
    }
    private void toLogin(){
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
    }

}