package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TriviaActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question);
        final Trivia q = new Trivia((int)getIntent().getExtras().get("difficulty"));
        TextView question = (TextView)findViewById(R.id.question);
        question.setText(q.getQuestion());
        Button ans1 = (Button)findViewById(R.id.ans1);
        Button ans2 = (Button)findViewById(R.id.ans2);
        Button ans3 = (Button)findViewById(R.id.ans3);
        Button ans4 = (Button)findViewById(R.id.ans4);
        String[] answers = q.getAnswers();
        ans1.setText("a)  " + answers[0]);
        ans2.setText("b)  " + answers[1]);
        ans3.setText("c)  " + answers[2]);
        ans4.setText("d)  " + answers[3]);

        findViewById(R.id.ans1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q.answerQuestion(0)){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("questin", q);
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
                if(q.answerQuestion(1)){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("questin", q);
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
                if(q.answerQuestion(2)){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("questin", q);
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
                if(q.answerQuestion(3)){
                    Intent i = new Intent(TriviaActivity.this, SuccessActivity.class);
                    i.putExtra("questin", q);
                    i.putExtra("difficulty", (int)getIntent().getExtras().get("difficulty"));
                    startActivity(i);                 }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
    }
}
