package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.root.ariel_trivia_app.adapters.CommentAdapter;
import com.example.root.ariel_trivia_app.adapters.TriviaAdapter;
import com.example.root.ariel_trivia_app.base.Trivia;

import java.util.ArrayList;
import java.util.List;

public class SelectTriviaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trivia);

        final List<Trivia> trivias = Global.apiRequests.requestTrivias(null, 10);
        ArrayList<String> trivia_questions = new ArrayList<>();
        for(Trivia t : trivias) {
            trivia_questions.add(t.getQuestion().getQuestion());
        }
        final ListView listView = (ListView) findViewById(R.id.lst_trivias);
        ListView lst = (ListView) findViewById(R.id.lst_trivias);
        TriviaAdapter adapter = new TriviaAdapter(trivias, getApplicationContext());
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trivia selected = trivias.get(position);

                Intent i = new Intent(SelectTriviaActivity.this, TriviaActivity.class);
                i.putExtra("trivia", selected);
                startActivity(i);
            }
        });

//        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
//        final RadioButton dif1 = (RadioButton)findViewById(R.id.dif1);
//
//        final RadioButton dif2 = (RadioButton)findViewById(R.id.dif2);
//
//        final RadioButton dif3 = (RadioButton)findViewById(R.id.dif3);
//
//        final RadioButton dif4 = (RadioButton)findViewById(R.id.dif4);
//
//        final RadioButton dif5 = (RadioButton)findViewById(R.id.dif5);
//
//        rg.check(dif1.getId());
//
//        findViewById(R.id.selectDif).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int difficulty;
//                if (dif1.isChecked()) {
//                    difficulty = 1;
//                }
//                else if (dif2.isChecked()) {
//                    difficulty = 2;
//                }
//                else if (dif3.isChecked()) {
//                    difficulty = 3;
//                }
//                else if (dif4.isChecked()) {
//                    difficulty = 4;
//                }
//                else {
//                    difficulty = 5;
//                }
//                Intent i = new Intent(SelectTriviaActivity.this, TriviaActivity.class);
//                i.putExtra("difficulty", difficulty);
//                startActivity(i);
//            }
//        });
    }
}
