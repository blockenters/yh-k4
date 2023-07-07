package com.block.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    ImageView imgAlarm;
    TextView txtTimer;
    EditText editTimer;
    Button btnCancel;
    Button btnStart;

    CountDownTimer timer;

    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAlarm = findViewById(R.id.imgAlarm);
        txtTimer = findViewById(R.id.txtTimer);
        editTimer = findViewById(R.id.editTimer);
        btnCancel = findViewById(R.id.btnCancel);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 유저가 입력한 시간을 가져온다.
                String strTime = editTimer.getText().toString().trim();
                time = Long.parseLong(strTime);
                time = time * 1000;

                // 2. 타이머를 만든다.
                timer = new CountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long l) {
                        long remain = l / 1000;
                        txtTimer.setText(""+remain);
                    }

                    @Override
                    public void onFinish() {
                        // 1. 소리
                        MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.alarm);
                        mp.start();
                        // 2. 에니메이션
                        YoYo.with(Techniques.Shake)
                                .duration(500)
                                .repeat(4)
                                .playOn( imgAlarm );
                    }
                };
                timer.start();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timer.cancel();

                txtTimer.setText( (time /1000) +"");

            }
        });
    }
}








