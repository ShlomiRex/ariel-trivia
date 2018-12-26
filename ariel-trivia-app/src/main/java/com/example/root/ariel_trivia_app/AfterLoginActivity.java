package com.example.root.ariel_trivia_app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class AfterLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);
        final Button answer = (Button) findViewById(R.id.userOptions_answer);
        final Button add = (Button) findViewById(R.id.userOptions_addQuestion);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLoginActivity.this, SelectTriviaActivity.class);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLoginActivity.this, AddTriviaQuestionActivity.class);
                startActivity(i);
            }
        });

    }

}
