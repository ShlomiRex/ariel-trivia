package com.example.root.ariel_trivia_app;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.adapters.CommentAdapter;
import com.example.root.ariel_trivia_app.base.Trivia;

public class CommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final Trivia trivia = (Trivia) getIntent().getSerializableExtra("trivia");

        ListView lst = (ListView) findViewById(R.id.comments_list);
        CommentAdapter adapter = new CommentAdapter(trivia.getForum().getComments(), getApplicationContext());

         TextView userView = lst.findViewById(R.id.user);
         TextView messageView = lst.findViewById(R.id.message);

         lst.setAdapter(adapter);

        findViewById(R.id.add_comment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.comment_place);
                //trivia.addComment(edit.getText().toString());
                //Global.apiRequests.postComment(trivia, "muser", "messag1");
                edit.setText("");
                Toast.makeText(getApplicationContext(), "comment added",Toast.LENGTH_LONG).show();
            }
        });
    }
}
