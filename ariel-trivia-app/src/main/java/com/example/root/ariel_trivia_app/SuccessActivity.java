package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;

public class SuccessActivity extends Activity {
    private static int RATE = 1;
    private Button btn_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);
        final Trivia trivia = (Trivia) getIntent().getSerializableExtra("trivia");
        final Button btn_like = findViewById(R.id.successActivity_btn_like);
        btn_rate = findViewById(R.id.successActivity_btn_rate);
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
                if(success) {
                    Toast.makeText(getApplicationContext(), R.string.succeedActivity_etxt_thank_for_like, Toast.LENGTH_LONG).show();
                    btn_like.setEnabled(false);
                }
                else
                    Toast.makeText(getApplicationContext(), R.string.succeedActivity_etxt_already_liked, Toast.LENGTH_LONG).show();
            }
        });
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, RateActivity.class);
                i.putExtra("trivia", trivia);
                startActivityForResult(i, RATE);

            }
        });

        if(Global.user.isGuest()) {
            btn_like.setEnabled(false);
            btn_rate.setEnabled(false);
        }

        if(trivia.getMetadata().getWhoLiked().contains(Global.user.getUsername())) {
            btn_like.setEnabled(false);
        }

        if(trivia.getMetadata().getWhoRated().contains(Global.user.getUsername())) {
            btn_rate.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RATE) {
            if(resultCode == RESULT_OK) {
                btn_rate.setEnabled(false);
            } else {

            }
        }
    }
}