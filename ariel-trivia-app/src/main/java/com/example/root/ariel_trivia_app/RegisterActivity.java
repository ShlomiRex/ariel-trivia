package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.data_models.user.LoginInfo;
import com.example.root.ariel_trivia_app.base.data_models.user.User;

import org.dizitart.no2.Nitrite;

public class RegisterActivity extends Activity {
    private Button btn_register;
    private EditText etxt_username;
    private EditText etxt_password;
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


        if(etxt_username.getText().toString().equals("")||  etxt_password.getText().toString().equals("")){
            btn_register.setEnabled(false);
        } else {
            btn_register.setEnabled(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Nitrite db = Global.database;

        btn_register = findViewById(R.id.register_btn_register);
        etxt_username = findViewById(R.id.register_etxt_username);
        etxt_password = findViewById(R.id.register_etxt_password);
        etxt_username.addTextChangedListener(mTextWatcher);
        etxt_password.addTextChangedListener(mTextWatcher);
        btn_register.setEnabled(false);
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