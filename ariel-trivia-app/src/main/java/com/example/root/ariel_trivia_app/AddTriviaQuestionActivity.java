package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;


public class AddTriviaQuestionActivity extends Activity {
    private NotificationCompat.Builder mBuilder;
    private NotificationManagerCompat notificationManager;

    private EditText questionET, correctAnswerET , wrong1ET, wrong2ET, wrong3ET;
    private Button btn_post;
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


        if(questionET.getText().toString().equals("")|| correctAnswerET.getText().toString().equals("")||
                wrong1ET.getText().toString().equals("")||wrong2ET.getText().toString().equals("")||wrong3ET.getText().toString().equals("")){
            btn_post.setEnabled(false);
        } else {
            btn_post.setEnabled(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);
        mBuilder = new NotificationCompat.Builder(this, "MyChannel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Title")
                .setContentText("Content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        setContentView(R.layout.activity_add_trivia_question);
        questionET = findViewById(R.id.addTrivia_etxt_question);
        correctAnswerET = findViewById(R.id.addTrivia_etxt_correctAnswer);
        wrong1ET = findViewById(R.id.addTrivia_etxt_wrontAnswer1);
        wrong2ET = findViewById(R.id.addTrivia_etxt_wrontAnswer2);
        wrong3ET = findViewById(R.id.addTrivia_etxt_wrontAnswer3);
        btn_post = findViewById(R.id.addTrivia_btn_post);
        questionET.addTextChangedListener(mTextWatcher);
        correctAnswerET.addTextChangedListener(mTextWatcher);
        wrong1ET.addTextChangedListener(mTextWatcher);
        wrong2ET.addTextChangedListener(mTextWatcher);
        wrong3ET.addTextChangedListener(mTextWatcher);
        btn_post.setEnabled(false);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question =  ((EditText) findViewById(R.id.addTrivia_etxt_question)).getText().toString();
                String answer =  ((EditText) findViewById(R.id.addTrivia_etxt_correctAnswer)).getText().toString();
                String[] wrongAnswers = new String[3];
                wrongAnswers[0] =  ((EditText) findViewById(R.id.addTrivia_etxt_wrontAnswer1)).getText().toString();
                wrongAnswers[1] =  ((EditText) findViewById(R.id.addTrivia_etxt_wrontAnswer2)).getText().toString();
                wrongAnswers[2] =  ((EditText) findViewById(R.id.addTrivia_etxt_wrontAnswer3)).getText().toString();
                Global.apiRequests.uploadTrivia(new Trivia(question, answer, wrongAnswers));
                Toast.makeText(getApplicationContext(), R.string.addTrivia_etxt_upload_succesfullString, Toast.LENGTH_LONG).show();

                mBuilder.setContentText("You successfuly uploaded the trivia.");
                mBuilder.setContentTitle("Upload Trivia");
                mBuilder.setSmallIcon(R.drawable.trivia_icon);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, mBuilder.build());

                finish();
            }
        });
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String cid = getString(R.string.channel_id);
            NotificationChannel channel = new NotificationChannel(cid, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
