package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final String TAG = MainActivity.class.getSimpleName();

    private final LoginInfo testLoginInfo = new LoginInfo("abc", "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final Button btn_login = (Button) findViewById(R.id.login_btn_login);
        final EditText etxt_username = (EditText) findViewById(R.id.login_etxt_username);
        final EditText etxt_password = (EditText) findViewById(R.id.login_etxt_password);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO login
                Intent i = new Intent(MainActivity.this, userOptionsActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.guest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SelectTriviaActivity.class);
                startActivity(i);
            };});
    }
}
