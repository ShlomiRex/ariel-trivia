package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mongo_network mongo_network = new mongo_network();
        mongo_network.execute();
    }
}
