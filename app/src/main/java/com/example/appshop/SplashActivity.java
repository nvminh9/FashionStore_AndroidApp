package com.example.appshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    boolean isRunning = false;
    int Max_sec = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUI();
        initListener();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
                progressBar.setProgress(0);
                while (progressBar.getProgress() < progressBar.getMax()){
                    progressBar.incrementProgressBy(20);
                }
            }
            //Time deplay của SplashActivity
        }, 2000);
    }

    private void initUI(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
    }

    private void initListener(){

    }

    private void nextActivity(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
//            progressBar.setVisibility(View.VISIBLE);
            //Chua dang nhap
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }else {
//            progressBar.setVisibility(View.VISIBLE);
            //Da dang nhap
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        finish();
    }
}
