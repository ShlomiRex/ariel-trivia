package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroupRate);
        final RadioButton rateDif1 = (RadioButton)findViewById(R.id.rateDif1);

        final RadioButton rateDif2 = (RadioButton)findViewById(R.id.rateDif2);

        final RadioButton rateDif3 = (RadioButton)findViewById(R.id.rateDif3);

        final RadioButton rateDif4 = (RadioButton)findViewById(R.id.rateDif4);

        rg.check(rateDif1.getId());

        Button button = (Button)findViewById(R.id.selectRate);
        findViewById(R.id.selectRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int difficulty;
                if (rateDif1.isChecked()) {
                    difficulty = 1;
                }
                else if (rateDif2.isChecked()) {
                    difficulty = 2;
                }
                else if (rateDif3.isChecked()) {
                    difficulty = 3;
                }
                else if (rateDif4.isChecked()) {
                    difficulty = 4;
                }
                else {
                    difficulty = 5;
                }
                TriviaActivity.q.rate(difficulty);
                SuccessActivity.rated.myBool = true;
                finish();
            }
        });
    }
}
