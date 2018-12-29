package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;


public class PostTriviaActivity extends Activity {

    private NotificationCompat.Builder mBuilder;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);

        setContentView(R.layout.activity_add_trivia_question);
        final Button btn_post = findViewById(R.id.addTrivia_btn_post);

        mBuilder = new NotificationCompat.Builder(this, "MyChannel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Title")
                .setContentText("Content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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
                Toast.makeText(getApplicationContext(), "Upload successful! You can now view it.", Toast.LENGTH_LONG).show();


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
