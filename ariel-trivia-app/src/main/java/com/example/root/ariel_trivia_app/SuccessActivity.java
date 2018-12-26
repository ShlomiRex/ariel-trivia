package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;

public class SuccessActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);
        final Trivia trivia = (Trivia) getIntent().getSerializableExtra("trivia");
        final Button btn_like = findViewById(R.id.successActivity_btn_like);
        final Button btn_rate = findViewById(R.id.successActivity_btn_rate);
        findViewById(R.id.successActivity_btn_nextQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.successActivity_btn_viewComments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, CommentActivity.class);
                i.putExtra("trivia", trivia);
                startActivity(i);
            }
        });
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = Global.apiRequests.likeTrivia(trivia);
                if(success)
                    Toast.makeText(getApplicationContext(), "Thank you for liking it!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "You already liked this question!", Toast.LENGTH_LONG).show();
            }
        });
        btn_rate.setOnClickListener(new View.OnClickListener() {
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

        if(Global.user.isGuest()) {
            btn_like.setEnabled(false);
            btn_rate.setEnabled(false);
        }
    }
}