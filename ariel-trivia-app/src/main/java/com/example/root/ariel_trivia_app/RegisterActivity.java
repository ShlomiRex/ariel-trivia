package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.user.LoginInfo;
import com.example.root.ariel_trivia_app.base.data_models.user.User;

import org.dizitart.no2.Nitrite;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Nitrite db = Global.database;

        final Button btn_register = findViewById(R.id.register_btn_register);
        final EditText etxt_username = findViewById(R.id.register_etxt_username);
        final EditText etxt_password = findViewById(R.id.register_etxt_password);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etxt_username.getText().toString();
                String password = etxt_password.getText().toString();
                String password_sha256 = Global.sha256(password);
                LoginInfo registerInfo = new LoginInfo(username, password_sha256);
                User userToRegister = new User(registerInfo, false);
                boolean success = Global.apiRequests.register(db, userToRegister);
                if(success) {
                    Toast.makeText(getApplicationContext(), R.string.registerActivity_etxt_success + username + R.string.registerActivity_etxt_can_login, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.registerActivity_etxt_register_failed, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}