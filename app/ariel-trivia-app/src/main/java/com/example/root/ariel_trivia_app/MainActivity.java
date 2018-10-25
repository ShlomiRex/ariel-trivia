package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTask mongo_network = new MyTask(getApplicationContext());
        mongo_network.execute();
    }
}
