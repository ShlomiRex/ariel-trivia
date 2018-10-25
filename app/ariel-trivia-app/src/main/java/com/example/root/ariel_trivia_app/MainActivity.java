package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Check if server is up
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


        //In testing
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MyMongo.getAllTrivias();
                return null;
            }
        }.execute();
    }
}
