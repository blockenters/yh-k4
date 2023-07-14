package com.block.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.block.contacts.adapter.ContactAdapter;
import com.block.contacts.data.DatabaseHandler;
import com.block.contacts.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    EditText editSearch;
    ImageView imgCancel;

    RecyclerView recyclerView;
    ContactAdapter adapter;
    ArrayList<Contact> contactArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);

        editSearch = findViewById(R.id.editSearch);
        imgCancel = findViewById(R.id.imgCancel);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keyword = editSearch.getText().toString().trim();

                // db에서 검색한후
                DatabaseHandler db = new DatabaseHandler(MainActivity.this, "contact_db", null, 1);

                contactArrayList.clear();

                contactArrayList.addAll( db.searchMemo(keyword) );

                // 결과를 화면에 보여준다.
                adapter.notifyDataSetChanged();
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "contact_db", null, 1);

                contactArrayList.clear();

                contactArrayList.addAll( handler.getAllContacts() );

                adapter.notifyDataSetChanged();

                editSearch.setText("");

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // contactArrayList 에 데이터 있어야 한다.
        // DB 에서 가져오면 된다.

        DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "contact_db", null, 1 );

        contactArrayList.clear();

        contactArrayList.addAll( handler.getAllContacts() );

        adapter = new ContactAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(adapter);

    }
}