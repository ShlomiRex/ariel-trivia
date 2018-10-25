package com.example.root.ariel_trivia_app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyMongo {
    private static String uri = "mongodb://Admin:Admin1@ds049219.mlab.com:49219/ariel-trivia";
    private static String TAG = "MyMongo";

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

    /**
     *
     * @return Array of JSONObjects that represents trivias. null if error occured.
     */
    public static List<Document> getAllTrivias() {
        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> d = database.getCollection("trivias");

            FindIterable<Document> itr = d.find();
            List<Document> lst = new ArrayList<>();
            itr.into(lst);

            mongoClient.close();

            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * In testing.
     * @param collection
     */
    public static void selectAllRecordsFromACollection(DBCollection collection)
    {
        DBCursor cursor = collection.find();
        while(cursor.hasNext())
        {
            System.out.println(cursor.next());
        }
    }

}
