package com.block.register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class AvatarActivity extends AppCompatActivity {

    ImageView imgAvatar;
    Button btnRabbit;
    Button btnTurtle;
    Button btnOk;

    boolean isSelected = false;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        email = getIntent().getStringExtra("email");

        imgAvatar = findViewById(R.id.imgAvatar);
        btnRabbit = findViewById(R.id.btnRabbit);
        btnTurtle = findViewById(R.id.btnTurtle);
        btnOk = findViewById(R.id.btnOk);

        btnRabbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAvatar.setImageResource(R.drawable.avatar1);
                isSelected = true;
            }
        });

        btnTurtle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAvatar.setImageResource(R.drawable.avatar2);
                isSelected = true;
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( !isSelected ){
                    Snackbar.make(btnOk,
                            "아바타를 꼭 선택하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                showAlertDialog();

            }
        });

    }


    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AvatarActivity.this);
        builder.setTitle("회원가입 완료");
        builder.setMessage("완료하시겠습니까?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // 새로운 액티비티를 띄운다.

                Intent intent = new Intent(AvatarActivity.this, WelcomeActivity.class);

                intent.putExtra("email", email);

                startActivity(intent);

                finish();

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 앱 종료
                finish();
            }
        });

        builder.show();
    }

}





