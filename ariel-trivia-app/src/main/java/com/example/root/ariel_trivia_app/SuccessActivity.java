package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SuccessActivity extends Activity {
    public static final boolObject rated = new boolObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);
        final boolObject like = new boolObject();
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, TriviaActivity.class);
                i.putExtra("difficulty", (int)getIntent().getExtras().get("difficulty"));
                finish();
                startActivity(i);
            }
        });
        findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, commentActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(like.myBool){
                    Toast.makeText(getApplicationContext(), "you pressed like already",Toast.LENGTH_LONG).show();
                }else {
                    TriviaActivity.q.addLike();
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
                    startActivity(i);
                }
            }
        });
    }
}
class boolObject{
    boolean myBool = false;
}
