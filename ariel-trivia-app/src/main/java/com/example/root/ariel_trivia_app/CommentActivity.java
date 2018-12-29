package com.example.root.ariel_trivia_app;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.adapters.CommentAdapter;
import com.example.root.ariel_trivia_app.base.data_models.trivia.Comment;
import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;

public class CommentActivity extends Activity {
    private EditText etxt_comment;
    private Button btn_add_comment;
    private static final String TAG = CommentActivity.class.getSimpleName();

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){


        if(etxt_comment.getText().toString().equals("")){
            btn_add_comment.setEnabled(false);
        } else {
            btn_add_comment.setEnabled(true);
        }
    }

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
        lst.setAdapter(adapter);

        etxt_comment = findViewById(R.id.commentActivity_etxt_commentMessage);
        btn_add_comment = findViewById(R.id.commentActivity_btn_addComment);
        etxt_comment.addTextChangedListener(mTextWatcher);
        btn_add_comment.setEnabled(false);
        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.commentActivity_etxt_commentMessage);
                String username = Global.user.getUsername();
                String message = edit.getText().toString();
                Global.apiRequests.postComment(db_trivia, new Comment(username, message));
                edit.setText("");
                Toast.makeText(getApplicationContext(), R.string.commentActivity_etxt_comment_added,Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });


        if(Global.user.isGuest()) {
            btn_add_comment.setEnabled(false);
            etxt_comment.setEnabled(false);
            etxt_comment.setHint(R.string.commentActivity_etxt_To_comment_you_must_log_in);
        }
    }
}
