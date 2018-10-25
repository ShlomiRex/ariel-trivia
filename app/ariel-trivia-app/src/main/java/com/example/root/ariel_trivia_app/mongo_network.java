package com.example.root.ariel_trivia_app;

import android.os.AsyncTask;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class mongo_network extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        String uri = "mongodb://Admin:Admin1@ds049219.mlab.com:49219/ariel-trivia";


        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            //MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> d = database.getCollection("test");
            d.insertOne(new Document("arye document", "rooor"));
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
