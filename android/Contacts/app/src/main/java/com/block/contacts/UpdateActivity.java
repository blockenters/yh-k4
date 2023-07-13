package com.block.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.block.contacts.data.DatabaseHandler;
import com.block.contacts.model.Contact;
import com.google.android.material.snackbar.Snackbar;

public class UpdateActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    Button btnSave;

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        contact = (Contact) getIntent().getSerializableExtra("contact");

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        editName.setText(contact.name);
        editPhone.setText(contact.phone);

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

                contact.name = name;
                contact.phone = phone;

                DatabaseHandler handler = new DatabaseHandler(UpdateActivity.this, "contact_db", null, 1);
                handler.updateContact(contact);

                Toast.makeText(UpdateActivity.this,
                        "잘 저장되었습니다.",
                        Toast.LENGTH_SHORT).show();

                finish();

            }
        });

    }
}


