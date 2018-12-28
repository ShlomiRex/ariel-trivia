package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;


public class AddTriviaQuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trivia_question);
        final Button btn_post = findViewById(R.id.addTrivia_btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question =  ((EditText) findViewById(R.id.addTrivia_etxt_question)).getText().toString();
                String answer =  ((EditText) findViewById(R.id.addTrivia_etxt_correctAnswer)).getText().toString();
                String[] wrongAnswers = new String[3];
                wrongAnswers[0] =  ((EditText) findViewById(R.id.addTrivia_etxt_wrontAnswer1)).getText().toString();
                wrongAnswers[1] =  ((EditText) findViewById(R.id.addTrivia_etxt_wrontAnswer2)).getText().toString();
                wrongAnswers[2] =  ((EditText) findViewById(R.id.addTrivia_etxt_wrontAnswer3)).getText().toString();
                Global.apiRequests.uploadTrivia(new Trivia(question, answer, wrongAnswers));
                Toast.makeText(getApplicationContext(), R.string.addTrivia_etxt_upload_succesfullString, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
