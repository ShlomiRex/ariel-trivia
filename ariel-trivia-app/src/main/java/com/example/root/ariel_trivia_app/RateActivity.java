package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;

public class RateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        final Trivia trivia = (Trivia) getIntent().getSerializableExtra("trivia");
        RadioGroup rg = (RadioGroup)findViewById(R.id.rate_radioGroup);
        final RadioButton rateDif1 = (RadioButton)findViewById(R.id.rate_rateDif1);
        final RadioButton rateDif2 = (RadioButton)findViewById(R.id.rate_rateDif2);
        final RadioButton rateDif3 = (RadioButton)findViewById(R.id.rate_rateDif3);
        final RadioButton rateDif4 = (RadioButton)findViewById(R.id.rate_rateDif4);
        final RadioButton rateDif5 = (RadioButton)findViewById(R.id.rate_rateDif5);

        final Button button = (Button)findViewById(R.id.rate_btn_ok);
        button.setEnabled(true);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button.setEnabled(true);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
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
                boolean successful = Global.apiRequests.rateTrivia(trivia, difficulty);
                if(successful) {
                    Toast.makeText(getApplicationContext(), "Rated successfuly", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",RESULT_OK);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Already rated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
