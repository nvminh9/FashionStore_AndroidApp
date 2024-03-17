package com.example.appshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ForgetPassActivity extends AppCompatActivity {

    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        initUI();
        initListener();
    }
    private void initUI(){
        back = (ImageButton) findViewById(R.id.imageButton);
    }
    private void initListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ForgetPassActivity.super.onBackPressed();
                Intent i = new Intent(ForgetPassActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}