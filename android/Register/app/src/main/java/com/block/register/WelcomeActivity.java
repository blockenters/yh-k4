package com.block.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        String email = getIntent().getStringExtra("email");

        SharedPreferences sp = getSharedPreferences("Register_App", MODE_PRIVATE);
        String email = sp.getString("email", "");


        txtWelcome = findViewById(R.id.txtWelcome);

        txtWelcome.setText(email+"님 환영합니다.");


    }
}