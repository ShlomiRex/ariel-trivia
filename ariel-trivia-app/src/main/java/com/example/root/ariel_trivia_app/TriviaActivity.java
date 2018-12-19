package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TriviaActivity extends Activity {
    public static final Trivia q = new Trivia();//(int)getIntent().getExtras().get("difficulty"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question);

        TextView question = (TextView)findViewById(R.id.question);
        question.setText(q.getQuestion().getQuestion());
        Button ans1 = (Button)findViewById(R.id.ans1);
        Button ans2 = (Button)findViewById(R.id.ans2);
        Button ans3 = (Button)findViewById(R.id.ans3);
        Button ans4 = (Button)findViewById(R.id.ans4);
        List<String> answers = q.getQuestion().getAnswers();
        ans1.setText("a)  " + answers.get(0));
        ans2.setText("b)  " + answers.get(1));
        ans3.setText("c)  " + answers.get(2));
        ans4.setText("d)  " + answers.get(3));

        findViewById(R.id.ans1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q.getQuestion().getCorrect_answer_index()==0){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("difficulty", (int)getIntent().getExtras().get("difficulty"));
                    startActivity(i);                }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
        findViewById(R.id.ans2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q.getQuestion().getCorrect_answer_index()==1){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("difficulty", (int)getIntent().getExtras().get("difficulty"));
                    startActivity(i);                 }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
        findViewById(R.id.ans3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q.getQuestion().getCorrect_answer_index()==2){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("difficulty", (int)getIntent().getExtras().get("difficulty"));
                    startActivity(i);                 }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
        findViewById(R.id.ans4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q.getQuestion().getCorrect_answer_index()==3){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("difficulty", (int)getIntent().getExtras().get("difficulty"));
                    startActivity(i);                 }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
    }
}
