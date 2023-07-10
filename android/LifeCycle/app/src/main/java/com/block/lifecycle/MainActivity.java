package com.block.lifecycle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editName;
    EditText editAge;

    TextView txtResult;

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {

                            // 데이터를 받아오는 코드는, 여기에다만 작성하면 된다.
                            if( result.getResultCode() == 1 ){

                                Log.i("LIFE MAIN", "onActivityResult");

                                int futureAge = result.getData().getIntExtra("data",0);
                                txtResult.setText(""+futureAge);
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("LIFE MAIN", "onCreate 실행됨.");

        button = findViewById(R.id.button);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        txtResult = findViewById(R.id.txtResult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString().trim();
                String strAge = editAge.getText().toString().trim();

                int age = Integer.parseInt(strAge);

                // SecondActivity를 실행시키고 싶다.

                // 새로운 액티비티를 실행시키고 싶으면,
                // 인텐트를 만들어야 한다.
                // 이 액티비티가 다른 액티비티를 실행시킨다!
                // 라는 의미로, 파라미터 설정해준다.
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // 데이터를 전달하기 위해서, 인텐트에 데이터를 담아줍니다.
                intent.putExtra("name" , name );
                intent.putExtra("age", age);

                // 아래 함수는 단방향으로 데이터 전달만 가능하고,
                // 내가 실행한 액티비티로부터 데이터를 받고 싶으면,
                // ActivityResultLauncher 클래스 이용해야 한다.
//                startActivity(intent);

                launcher.launch(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("LIFE MAIN", "onStart 실행됨.");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("LIFE MAIN", "onResume 실행됨.");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("LIFE MAIN", "onPause 실행됨.");

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("LIFE MAIN", "onStop 실행됨.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("LIFE MAIN", "onDestroy 실행됨.");
    }
}


