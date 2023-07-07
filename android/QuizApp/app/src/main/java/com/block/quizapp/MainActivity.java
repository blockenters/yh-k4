package com.block.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.block.quizapp.model.Quiz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtQuiz;
    ProgressBar progressBar;
    TextView txtResult;
    Button btnTrue;
    Button btnFalse;

    // 퀴즈 저장용 멤버변수
    ArrayList<Quiz> quizArrayList = new ArrayList<>();

    int currentQuizIndex = 0;

    Quiz quiz;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtQuiz = findViewById(R.id.txtQuiz);
        progressBar = findViewById(R.id.progressBar);
        txtResult = findViewById(R.id.txtResult);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);


        // 퀴즈를 내야 한다.
        // 퀴즈를 먼저 메모리에 변수로 만들어야 한다.
        // 퀴즈가 여러개니까, 여러개를 하나의 변수로 처리할수 있는
        // 데이터 스트럭쳐가 필요하고,
        // 자바의 가장 유용한 데이터스트럭쳐, 어레이리스트 사용한다.
        setQuiz();

        progressBar.setProgress(currentQuizIndex+1, true);

        // 문제를 출제한다.
        quiz = quizArrayList.get(currentQuizIndex);
        // 화면에 표시
        txtQuiz.setText(quiz.question);
        
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !getQuiz() ){
                    return;
                };

                if (quiz.answer == true ){
                    txtResult.setText("정답입니다.");
                    count = count + 1;
                } else {
                    txtResult.setText("오답입니다.");
                }


            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !getQuiz() ){
                    return;
                };

                if (quiz.answer == false){
                    txtResult.setText("정답입니다.");
                    count = count + 1;
                } else {
                    txtResult.setText("오답입니다.");
                }



            }
        });


    }

    private boolean getQuiz() {

        Log.i("QUIZ MAIN", currentQuizIndex +"" );

        if (currentQuizIndex >= quizArrayList.size()){

            txtResult.setText("지금까지 맞춘 문제는 "+count+"개입니다.");

            return false;
        }

        quiz = quizArrayList.get(currentQuizIndex);
        txtQuiz.setText(quiz.question);

        progressBar.setProgress(currentQuizIndex+1, true);

        currentQuizIndex = currentQuizIndex + 1;

        return true;

    }


    void setQuiz(){
        Quiz q1 = new Quiz(R.string.q1, true);
        quizArrayList.add(q1);
        Quiz q2 = new Quiz(R.string.q2, false);
        quizArrayList.add(q2);
        Quiz q3 = new Quiz(R.string.q3, true);
        quizArrayList.add(q3);
        Quiz q4 = new Quiz(R.string.q4, false);
        quizArrayList.add(q4);
        Quiz q5 = new Quiz(R.string.q5, true);
        quizArrayList.add(q5);
        Quiz q6 = new Quiz(R.string.q6, false);
        quizArrayList.add(q6);
        Quiz q7 = new Quiz(R.string.q7, true);
        quizArrayList.add(q7);
        Quiz q8 = new Quiz(R.string.q8, false);
        quizArrayList.add(q8);
        Quiz q9 = new Quiz(R.string.q9, true);
        quizArrayList.add(q9);
        Quiz q10 = new Quiz(R.string.q10, false);
        quizArrayList.add(q10);
    }

}