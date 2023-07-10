package com.block.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtContent;
    TextView txtFuture;

    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i("LIFE SECOND", "두번째 액티비티의 onCreate 실행");

        // 받아오는 데이터가 있으면, 데이터를 받아온다.
        String name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 0);

        txtContent = findViewById(R.id.txtContent);
        txtFuture = findViewById(R.id.txtFuture);

        txtContent.setText("이름은 " + name + "이고, 나이는 "+age +"입니다.");
        txtFuture.setText("10년후의 나이는 "+ (age+10) + "입니다."  );



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LIFE SECOND", "두번째 액티비티의 onStart 실행");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LIFE SECOND", "두번째 액티비티의 onResume 실행");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LIFE SECOND", "두번째 액티비티의 onPause 실행");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LIFE SECOND", "두번째 액티비티의 onStop 실행");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LIFE SECOND", "두번째 액티비티의 onDestroy 실행");
    }

    @Override
    public void onBackPressed() {

        // 백버튼 눌렀을때 처리해주는 함수가 이미 있으니,
        // 여기에 작성하면 된다.

        Log.i("LIFE SECOND", "두번째 액티비티의 onBackPressed 실행");

        // 나이를 10 더한후에, 이 액티비티를 실행한 액티비티에게
        // 데이터를 전달!
        int data = age + 10;

        Intent intent = new Intent();
        intent.putExtra("data", data);

        setResult(1, intent);

        // 이 코드는 내 코드 다 작성후에, 맨 마지막에 넣는다.
        super.onBackPressed();

    }
}






