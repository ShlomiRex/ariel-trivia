package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class trivia_question extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question);
        final question q = question.getQuestion();
        TextView question = (TextView)findViewById(R.id.question);
        question.setText(q._question);
        Button ans1 = (Button)findViewById(R.id.ans1);
        Button ans2 = (Button)findViewById(R.id.ans2);
        Button ans3 = (Button)findViewById(R.id.ans3);
        Button ans4 = (Button)findViewById(R.id.ans4);
        ans1.setText("a)  " + q._ans1);
        ans2.setText("b)  " + q._ans2);
        ans3.setText("c)  " + q._ans3);
        ans4.setText("d)  " + q._ans4);

        findViewById(R.id.ans1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q._trueAns == 1){
                    Toast.makeText(getApplicationContext(), "you are right!!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
        findViewById(R.id.ans2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q._trueAns == 2){
                    Toast.makeText(getApplicationContext(), "you are right!!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
        findViewById(R.id.ans3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q._trueAns == 3){
                    Toast.makeText(getApplicationContext(), "you are right!!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
        findViewById(R.id.ans4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q._trueAns == 4){
                    Toast.makeText(getApplicationContext(), "you are right!!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "you failed!!",Toast.LENGTH_LONG).show();
                }
            };
        });
    }
}
