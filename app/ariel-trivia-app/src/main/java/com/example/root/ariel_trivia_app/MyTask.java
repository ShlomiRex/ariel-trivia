package com.example.root.ariel_trivia_app;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    public MyTask(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return new Boolean(MyMongo.isUp());
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        boolean isup = aBoolean.booleanValue();
        if(isup) {
            Toast.makeText(context, "MongoDB server is UP.",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "MongoDB server is DOWN.",Toast.LENGTH_LONG).show();
        }
    }


}
