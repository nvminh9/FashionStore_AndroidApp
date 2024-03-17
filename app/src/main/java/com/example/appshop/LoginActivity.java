package com.example.appshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnNext, btnForgotPass, btnCreateAcc;
    private EditText txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initListener();
    }

    private void initUI(){
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnForgotPass = (Button) findViewById(R.id.btnForgotPass);
        btnCreateAcc = (Button) findViewById(R.id.btnCreateAcc);

        //progressDialog = new ProgressDialog(this);
    }
    private void initListener(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPass();
            }
        });
        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void login(){
        String strEmail = txtEmail.getText().toString().trim();
        String strPassword = txtPassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //progressDialog.show();
        if(TextUtils.isEmpty(strEmail) && TextUtils.isEmpty(strPassword)){
            Toast.makeText(this,"Please enter Email and Password !", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(strEmail)){
            Toast.makeText(this,"Please enter Email !", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(strPassword)){
            Toast.makeText(this,"Please enter Password !", Toast.LENGTH_SHORT).show();
            return;
        }else {
            auth.signInWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Đăng nhập thành công sau đó chuyển qua main activity với dữ liệu của user đăng nhập
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, SplashActivity.class);
                                startActivity(i);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                //Đăng nhập ko thành công sẽ hiện thông báo
                                Toast.makeText(LoginActivity.this, "Wrong email or password !" + strEmail + strPassword, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
//        //Login thành công
//        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(i);
    }

    private void forgotPass(){
        //Qua activity quên mật khẩu
        Intent i = new Intent(LoginActivity.this, ForgetPassActivity.class);
        startActivity(i);
    }

    private void register(){
        //qua activity đăng ký
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }

}