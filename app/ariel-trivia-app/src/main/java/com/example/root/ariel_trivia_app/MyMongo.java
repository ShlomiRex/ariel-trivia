package com.example.root.ariel_trivia_app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.common.hash.Hashing;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

/**
 * All public functions in this class are static AND are network tasks.
 */
public class MyMongo {
    private static String uri = "mongodb://Admin:Admin1@ds049219.mlab.com:49219/ariel-trivia";
    private static String TAG = "MyMongo";

    /**
     * Check if server is up or down.
     * @return True if the server works.
     */
    public static boolean isUp() {
        try {
            long before = System.currentTimeMillis();

            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> d = database.getCollection("test");
            d.count();
            mongoClient.close();

            long after = System.currentTimeMillis();
            double delta = (after-before)/1000;
            Log.d(TAG, "isUp - Query time: " + delta);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
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
            long before = System.currentTimeMillis();

            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> d = database.getCollection("trivias");

            FindIterable<Document> itr = d.find();
            List<Document> lst = new ArrayList<>();
            itr.into(lst);

            mongoClient.close();

            long after = System.currentTimeMillis();
            double delta = (after-before)/1000;
            Log.d(TAG, "getAllTrivias - Query time: " + delta);

            return lst;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param username The email of the user. (The email = username)
     * @param password The password of the user. Unencrypted.
     * @return True if successful. False is failed.
     */
    public static boolean register_user(String username, String password) {
        String enc_pass = sha256(password);

        try {
            long before = System.currentTimeMillis();
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> users_col = database.getCollection("users");


            JSONObject doc = new JSONObject();
            doc.put("username", username);
            doc.put("password", enc_pass);

            users_col.insertOne(Document.parse(doc.toString()));

            mongoClient.close();

            long after = System.currentTimeMillis();
            double delta = (after-before)/1000;
            Log.d(TAG, "register_user - Query time: " + delta);

            return true;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }


    }

    /**
     *
     * @param username The email of the user. (The email = username)
     * @param password The password of the user. Unencrypted.
     * @return True if successful. False is failed.
     */
    public static boolean login_user(String username, String password) {
        String enc_pass = sha256(password);

        try {
            long before = System.currentTimeMillis();
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> users_col = database.getCollection("users");

            Document doc = users_col.find(eq("username",username)).first();
            String pass_sha256 = (String) doc.get("password");
            if(pass_sha256.equals(enc_pass)) {
                mongoClient.close();
                long after = System.currentTimeMillis();
                double delta = (after-before)/1000;
                Log.d(TAG, "register_user - Query time: " + delta);
                return true;
            } else{
                mongoClient.close();
                long after = System.currentTimeMillis();
                double delta = (after-before)/1000;
                Log.d(TAG, "register_user - Query time: " + delta);
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }


    }

    /**
     * Encrpy message using SHA 256 hashing algorithem
     * @param message The message to encrypt
     * @return Returns 256 byte encrypted message. Returns String for ease of use.
     */
    private static String sha256(String message) {
        return Hashing.sha256()
                .hashString(message, StandardCharsets.UTF_8)
                .toString();
    }

}
