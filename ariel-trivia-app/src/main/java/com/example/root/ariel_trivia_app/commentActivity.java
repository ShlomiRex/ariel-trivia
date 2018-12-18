package com.example.root.ariel_trivia_app;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class commentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        TextView tv = (TextView)findViewById(R.id.commentsText);
        tv.setText(TriviaActivity.q.getForum().toString());
    }
}
