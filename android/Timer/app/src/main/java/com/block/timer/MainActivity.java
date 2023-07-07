package com.block.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtTimer;
    TextView txtCount;
    Button button;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = findViewById(R.id.txtTimer);
        txtCount = findViewById(R.id.txtCount);
        button = findViewById(R.id.button);

        // 타이머를 만든다.
        CountDownTimer timer = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long l) {
                // 위의 인터벌 파라미터에 의해서 실행되는 함수다.

                // 남은시간을 화면에 표시한다!!

                long remain = l / 1000;

                txtTimer.setText( remain +"초" );
            }

            @Override
            public void onFinish() {
                // 타이머가 종료될때 실행되는 함수이므로,
                // 종료될때 하고싶은 코드를 여기에 작성.

                // 알러트 다이얼로그 띄어서, 다시 도전할지, 종료할지 보여줄것.

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;
                txtCount.setText(count+"번");
            }
        });


        timer.start();

    }
}





