package com.block.simplememo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.block.simplememo.adapter.MemoAdapter;
import com.block.simplememo.model.Memo;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editMemo;
    Button btnSave;

    // 리사이클러뷰는, 함께 사용하는 변수들이 있다.
    // 7. 어댑터를 만든후에, 리사이클러뷰 관련 변수들을 작성한다.
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMemo = findViewById(R.id.editMemo);
        btnSave = findViewById(R.id.btnSave);

        recyclerView = findViewById(R.id.recyclerView);
        // 8. 리사이클러뷰 초기화 작업
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        // 메모버튼 누르면, 메모 생성해서, 화면에 나오도록 개발.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 유저가 입력한 메모를 가져온다.
                String content = editMemo.getText().toString().trim();

                if(content.isEmpty()){
                    Snackbar.make(btnSave,
                            "메모는 필수입니다.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // 2. 메모 클래스를 객체생성하고, 데이터를 저장한다.
                Memo memo = new Memo();
                memo.content = content;

                // 3. 메모가 여러개이므로, 어레이리스트에 넣어준다.

                memoList.add(memo);

                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);

            }
        });

    }

}


