package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.root.ariel_trivia_app.adapters.TriviaAdapter;
import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;

import java.util.ArrayList;
import java.util.List;

public class SelectTriviaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trivia);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final List<Trivia> trivias = Global.apiRequests.requestTrivias(null, 10);
        ArrayList<String> trivia_questions = new ArrayList<>();
        for(Trivia t : trivias) {
            trivia_questions.add(t.getQuestion().getQuestion());
        }
        final ListView listView = findViewById(R.id.lst_trivias);
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
    }


}
