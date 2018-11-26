package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SuccessActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);
        final boolObject rated = new boolObject();
        //rated.myBool = false;
        final boolObject like = new boolObject();
        //like.myBool = false;
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, TriviaActivity.class);
                i.putExtra("difficulty", (int)getIntent().getExtras().get("difficulty"));
                startActivity(i);
            }
        });
        findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, commentActivity.class);
                i.putExtra("question", (int)getIntent().getExtras().get("question"));
                startActivity(i);
            }
        });
        findViewById(R.id.like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(like.myBool){
                    Toast.makeText(getApplicationContext(), "you pressed like already",Toast.LENGTH_LONG).show();
                }else {
                    ((Trivia)getIntent().getExtras().get("question")).addLike();
                    like.myBool = true;
                }
            }
        });
        findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rated.myBool){
                    Toast.makeText(getApplicationContext(), "you rated already",Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(SuccessActivity.this, RateActivity.class);
                    i.putExtra("question", (int) getIntent().getExtras().get("question"));
                    startActivity(i);
                }
            }
        });
    }
}
