package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SelectTriviaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trivia);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        final RadioButton dif1 = (RadioButton)findViewById(R.id.dif1);

        final RadioButton dif2 = (RadioButton)findViewById(R.id.dif2);

        final RadioButton dif3 = (RadioButton)findViewById(R.id.dif3);

        final RadioButton dif4 = (RadioButton)findViewById(R.id.dif4);

        final RadioButton dif5 = (RadioButton)findViewById(R.id.dif5);

        rg.check(dif1.getId());

        Button button = (Button)findViewById(R.id.selectDif);
        findViewById(R.id.selectDif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int difficulty;
                if (dif1.isChecked()) {
                    difficulty = 1;
                }
                else if (dif2.isChecked()) {
                    difficulty = 2;
                }
                else if (dif3.isChecked()) {
                    difficulty = 3;
                }
                else if (dif4.isChecked()) {
                    difficulty = 4;
                }
                else {
                    difficulty = 5;
                }
                Intent i = new Intent(SelectTriviaActivity.this, TriviaActivity.class);
                i.putExtra("difficulty", difficulty);
                startActivity(i);
            }
        });
    }
}
