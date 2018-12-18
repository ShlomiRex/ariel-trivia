package com.example.root.ariel_trivia_app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class userOptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);
        final Button answer = (Button) findViewById(R.id.answer);
        final Button add = (Button) findViewById(R.id.add_question);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userOptionsActivity.this, SelectTriviaActivity.class);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userOptionsActivity.this, addTriviaQuestionActivity.class);
                startActivity(i);
            }
        });

    }

}
