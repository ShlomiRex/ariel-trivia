package com.example.root.ariel_trivia_app;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class commentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        TextView tv = (TextView)findViewById(R.id.forum);
        tv.setText(TriviaActivity.q.getForum().toString());
        findViewById(R.id.add_comment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.comment_place);
                TriviaActivity.q.addComment(edit.getText().toString());
                edit.setText("write your comment");
                Toast.makeText(getApplicationContext(), "comment added",Toast.LENGTH_LONG).show();
            }
        });
    }
}
