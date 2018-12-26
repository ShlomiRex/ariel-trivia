package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;


public class addTriviaQuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trivia_question);
        final Button add = (Button) findViewById(R.id.add_question_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question =  ((EditText) findViewById(R.id.myQuestion)).getText().toString();
                String answer =  ((EditText) findViewById(R.id.correctAnswer)).getText().toString();
                String[] wrongAnswers = new String[3];
                wrongAnswers[0] =  ((EditText) findViewById(R.id.myFirstAnswer)).getText().toString();
                wrongAnswers[1] =  ((EditText) findViewById(R.id.mySecondAnswer)).getText().toString();
                wrongAnswers[2] =  ((EditText) findViewById(R.id.myThirdAnswer)).getText().toString();
                Global.apiRequests.uploadTrivia(new Trivia(question, answer, wrongAnswers));
                Intent i = new Intent(addTriviaQuestionActivity.this, addTriviaQuestionActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}
