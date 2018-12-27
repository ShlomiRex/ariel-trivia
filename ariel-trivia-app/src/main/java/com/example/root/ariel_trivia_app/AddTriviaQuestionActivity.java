package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;


public class AddTriviaQuestionActivity extends Activity {
    private EditText questionET, correctAnswerET , wrong1ET, wrong2ET, wrong3ET;
    private Button btn_post;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){


        if(questionET.getText().toString().equals("")|| correctAnswerET.getText().toString().equals("")||
                wrong1ET.getText().toString().equals("")||wrong2ET.getText().toString().equals("")||wrong3ET.getText().toString().equals("")){
            btn_post.setEnabled(false);
        } else {
            btn_post.setEnabled(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trivia_question);
        questionET = findViewById(R.id.addTrivia_etxt_question);
        correctAnswerET = findViewById(R.id.addTrivia_etxt_correctAnswer);
        wrong1ET = findViewById(R.id.addTrivia_etxt_wrontAnswer1);
        wrong2ET = findViewById(R.id.addTrivia_etxt_wrontAnswer2);
        wrong3ET = findViewById(R.id.addTrivia_etxt_wrontAnswer3);
        btn_post = findViewById(R.id.addTrivia_btn_post);
        questionET.addTextChangedListener(mTextWatcher);
        correctAnswerET.addTextChangedListener(mTextWatcher);
        wrong1ET.addTextChangedListener(mTextWatcher);
        wrong2ET.addTextChangedListener(mTextWatcher);
        wrong3ET.addTextChangedListener(mTextWatcher);
        btn_post.setEnabled(false);
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
                Toast.makeText(getApplicationContext(), "Upload successful! You can now view it.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
