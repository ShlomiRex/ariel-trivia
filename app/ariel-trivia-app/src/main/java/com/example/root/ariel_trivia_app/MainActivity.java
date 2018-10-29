package com.example.root.ariel_trivia_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //Login (Works)
        final Button btn_login = (Button) findViewById(R.id.login_btn_login);
        final EditText etxt_username = (EditText) findViewById(R.id.login_etxt_username);
        final EditText etxt_password = (EditText) findViewById(R.id.login_etxt_password);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        String username = etxt_username.getText().toString();
                        String password = etxt_password.getText().toString();
                        //TODO: Can  restrict password length, check email is valid and so on.
                        return new Boolean(MyMongo.login_user(username, password));
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        boolean res = aBoolean.booleanValue();
                        if (res) {
                            //If true
                            Toast.makeText(getApplicationContext(), "Successfuly Login!", Toast.LENGTH_LONG).show();
                        } else {
                            //Failed...
                            Toast.makeText(getApplicationContext(), "Login failed!! OH NO!!", Toast.LENGTH_LONG).show();
                        }
                    }


                }.execute();
            }
        });

        findViewById(R.id.guest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, select_trivia.class);
                startActivity(i);
            };});
    }
}

        //*/
        //For testing
        /*
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<Document> tmp = MyMongo.getAllTrivias();
                List<Trivia> trivias = new ArrayList<>();
                for(Document d : tmp) {
                    Log.d(TAG, new Trivia(d).toString());
                }
                return null;
            }
        }.execute();



        Button main_btn_isUp = (Button) findViewById(R.id.main_btn_isUp);
        main_btn_isUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        MyMongo.isUp();
                        return null;
                    }
                }.execute();
            }
        });
    }
}
//Check if server is up (Works)
        /*
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return new Boolean(MyMongo.isUp());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                boolean isup = aBoolean.booleanValue();
                if(isup) {
                    Toast.makeText(getApplicationContext(), "MongoDB server is UP.",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "MongoDB server is DOWN.",Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
        */


//Get all trivias (works)
        /*
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MyMongo.getAllTrivias();
                return null;
            }
        }.execute();
        */


//Register (Works)
        /*
        final Button btn_register = (Button)findViewById(R.id.btn_register);
        final EditText etxt_username = (EditText)findViewById(R.id.etxt_username);
        final EditText etxt_password = (EditText)findViewById(R.id.etxt_password);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        String username = etxt_username.getText().toString();
                        String password = etxt_password.getText().toString();
                        //TODO: Can  restrict password length, check email is valid and so on.
                        return new Boolean(MyMongo.register_user(username, password));
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        boolean res = aBoolean.booleanValue();
                        if(res) {
                            //If true
                            Toast.makeText(getApplicationContext(), "Successfuly registered!",Toast.LENGTH_LONG).show();
                        } else {
                            //Failed...
                            Toast.makeText(getApplicationContext(), "Registeration failed!! OH NO!!",Toast.LENGTH_LONG).show();
                        }
                    }


                }.execute();
            }
        });

       */

