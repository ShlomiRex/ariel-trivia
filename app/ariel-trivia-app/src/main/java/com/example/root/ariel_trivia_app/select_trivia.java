package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class select_trivia extends Activity {

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
                question q;
                if (dif1.isChecked()) {
                    q = question.getQuestion(1);
                }
                else if (dif2.isChecked()) {
                    q = question.getQuestion(2);
                }
                else if (dif3.isChecked()) {
                    q = question.getQuestion(3);
                }
                else if (dif4.isChecked()) {
                    q = question.getQuestion(4);
                }
                else {
                    q = question.getQuestion(5);
                }
                Intent i = new Intent(select_trivia.this, trivia_question.class);
                i.putExtra("question", q);
                startActivity(i);
            }
        });
    }
}
