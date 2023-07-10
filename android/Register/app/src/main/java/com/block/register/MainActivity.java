package com.block.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword1;
    EditText editPassword2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword1 = findViewById(R.id.editPassword1);
        editPassword2 = findViewById(R.id.editPassword2);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password1 = editPassword1.getText().toString().trim();
                String password2 = editPassword2.getText().toString().trim();

                if(email.isEmpty() || password1.isEmpty() || password2.isEmpty()){
                    Snackbar.make(btnRegister,
                            "필수항목을 모두 입력하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

//                if( !email.contains("@")  ){
                
                if( email.contains("@") == false ){
                    Snackbar.make(btnRegister,
                            "이메일 형식을 확인하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(  password1.length() < 4 || password1.length() > 12  ){
                    Snackbar.make(btnRegister,
                            "비밀번호 길이를 확인하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(  !password1.equals(password2)  ){
                    Snackbar.make(btnRegister,
                            "비밀번호가 서로 다릅니다.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // 아바타 선택하는 액티비티를 띄운다.
                Intent intent = new Intent(MainActivity.this, AvatarActivity.class);

                startActivity(intent);

                finish();

            }
        });

    }
}






