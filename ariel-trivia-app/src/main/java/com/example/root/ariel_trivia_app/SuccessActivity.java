package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;

public class SuccessActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);
        final Trivia trivia = (Trivia) getIntent().getSerializableExtra("trivia");

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, CommentActivity.class);
                i.putExtra("trivia", trivia);
                startActivity(i);
            }
        });
        findViewById(R.id.like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = Global.apiRequests.likeTrivia(trivia);
                if(success)
                    Toast.makeText(getApplicationContext(), "Thank you for liking it!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "You already liked this question!", Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(rated.myBool){
//                    Toast.makeText(getApplicationContext(), "you rated already",Toast.LENGTH_LONG).show();
//                }else {
//                    Intent i = new Intent(SuccessActivity.this, RateActivity.class);
//                    startActivity(i);
//                }
                Toast.makeText(getApplicationContext(), "Not implimented yet", Toast.LENGTH_LONG).show();
            }
        });
    }
}