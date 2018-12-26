package com.example.root.ariel_trivia_app;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.adapters.CommentAdapter;
import com.example.root.ariel_trivia_app.base.Comment;
import com.example.root.ariel_trivia_app.base.Trivia;

import java.util.List;

public class CommentActivity extends Activity {

    private static final String TAG = CommentActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        super.onResume();
        final Trivia trivia = (Trivia) getIntent().getSerializableExtra("trivia");
        String id = trivia.getId();
        ListView lst = findViewById(R.id.comments_list);
        final Trivia db_trivia = Global.apiRequests.getTrivia(id);
        if(db_trivia == null) {
            Log.e(TAG, "No such trivia id");
            finish();
        }



        final CommentAdapter adapter = new CommentAdapter(db_trivia.getForum().getComments(), getApplicationContext());

        TextView userView = lst.findViewById(R.id.user);
        TextView messageView = lst.findViewById(R.id.message);
        lst.setAdapter(adapter);

        findViewById(R.id.add_comment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.comment_place);
                String username = Global.user.getUsername();
                String message = edit.getText().toString();
                Global.apiRequests.postComment(db_trivia, new Comment(username, message));
                edit.setText("");
                Toast.makeText(getApplicationContext(), "comment added",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
