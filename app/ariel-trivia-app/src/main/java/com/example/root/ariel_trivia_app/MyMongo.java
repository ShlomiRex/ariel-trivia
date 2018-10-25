package com.example.root.ariel_trivia_app;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.concurrent.Callable;

public class MyMongo {
    private static String uri = "mongodb://Admin:Admin1@ds049219.mlab.com:49219/ariel-trivia";

    /**
     * Check if server is up or down.
     * @return True if the server works.
     */
    public static boolean isUp() {
        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> d = database.getCollection("test");
            d.insertOne(new Document("arye document", "rooor"));
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
