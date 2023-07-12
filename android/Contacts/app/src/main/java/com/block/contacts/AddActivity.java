package com.block.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.block.contacts.data.DatabaseHandler;
import com.block.contacts.model.Contact;
import com.google.android.material.snackbar.Snackbar;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Snackbar.make(btnSave,
                            "필수항목을 입력하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // 이름과 전화번호가 있으니까, 저장 => 메모리!!
                Contact contact = new Contact(name, phone);

                // DB에 저장한다.
                // 1. db hander 를 만든다.
                DatabaseHandler handler = new DatabaseHandler(AddActivity.this, "contact_db", null, 1 );
                handler.addContact(contact);

                Toast.makeText(AddActivity.this,
                        "잘 저장되었습니다.",
                        Toast.LENGTH_SHORT).show();

                finish();

            }
        });

    }
}





