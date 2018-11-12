package Handlers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import org.bson.Document;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class Cookie {


    private static enum rCode {
        wrongCookie(1), noSuchCookieOrExpiredCookie(2),  invalidCookieRequest(3);

        private final int value;
        private rCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    };

    private static final String CHAR_LIST =
            "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Exact length of cookie.
     */
    public static final int LENGTH = 16;
    private static final String DB_COLLECTION_NAME = "cookies";

    private static final int EXPIRE_SECONDS = 600; //TODO: Change

    /**
     * Get random cookie with length LENGTH.
     * @return
     */
    public static String getCookie() {
        return generateRandomStringUsingSecureRandom(LENGTH);
    }

    public static void removeCookie(String db_uri, String username) {
        MongoClientURI mongoClientURI = new MongoClientURI(db_uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
        MongoCollection<Document> cookies_col = database.getCollection(DB_COLLECTION_NAME);
        Document doc = cookies_col.find(eq("username",username)).first();
        if(doc == null) {
            System.out.println("Cookie already removed for username: " + username);
            mongoClient.close();
            return;
        }
        System.out.println("Removed cookie: " + doc.toJson());
        cookies_col.deleteOne(doc);
        mongoClient.close();
    }

    /**
     *
     * @param db_uri
     * @param cookie
     * @param username
     * @return 0 - Cookie invalid, 1 - Cookie valid, 2 - No such cookie / expired, 3 - Invalid request
     */
    public static int validateCookie(String db_uri, String cookie, String username) {
        System.out.println("Validating cookie...");
        if(username == null || cookie == null) {
            return 3;
        }
        if(cookie.length() != LENGTH) {
            return 0;
        }

        MongoClientURI mongoClientURI = new MongoClientURI(db_uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
        MongoCollection<Document> cookies_col = database.getCollection(DB_COLLECTION_NAME);

        Document doc = cookies_col.find(and(eq("username",username),eq("cookie",cookie))).first();

        if(doc == null) {
            mongoClient.close();
            return 2;
        }
        Long expire = doc.getLong("expire");
        long curr_unix_time = System.currentTimeMillis();
        if(curr_unix_time > expire.longValue()) {
            //Cookie expired
            //Remove from db!
            long delta = (curr_unix_time - expire.longValue()) / 1000;
            System.out.println("Cookie is expired! Delta seconds:" + delta);
            System.out.println("Removed cookie: " + doc.toJson());
            cookies_col.deleteOne(doc);
            mongoClient.close();
            return 2;
        }
        mongoClient.close();
        return 1;
    }

    /**
     * This method generates random string
     * @return
     */
    private static String generateRandomStringUsingSecureRandom(int length){
        StringBuffer randStr = new StringBuffer(length);
        SecureRandom secureRandom = new SecureRandom();
        for( int i = 0; i < length; i++ )
            randStr.append( CHAR_LIST.charAt( secureRandom.nextInt(CHAR_LIST.length()) ) );
        return randStr.toString();
    }

    public static void insertCookieDocument(String db_uri, String cookie, String username) {
        long timestamp = System.currentTimeMillis();
        Document doc = new Document();
        doc.put("username",username);
        doc.put("cookie", cookie);
        doc.put("expire", timestamp + EXPIRE_SECONDS * 1000);


        MongoClientURI mongoClientURI = new MongoClientURI(db_uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
        MongoCollection<Document> cookies_col = database.getCollection(DB_COLLECTION_NAME);

        cookies_col.insertOne(doc);

        mongoClient.close();
    }

    /**
     * Validates cookie, sends response to client if not valid, saves duplicate code for each handler.
     * @param he
     * @param db_uri
     * @return True - if cookie is valid and the user is auth OK, False - if the cookie is invalid / invalid request / i dont know :(
     * @throws IOException
     */
    public static boolean validateCookie(HttpExchange he, String db_uri) throws IOException {

        Map<String, Object> map = Query.parseBodyQuery(he.getRequestBody());

        //Validate cookie
        String cookie = (String) map.get("cookie");
        String username = (String) map.get("username");
        int code = Cookie.validateCookie(db_uri, cookie, username);
        if(code != 1) {
            if(code == 0) {
                System.out.println("Username " + username + " entered wrong cookie!");
                Query.sendResponse(he, "Wrong cookie", rCode.wrongCookie.getValue());
            }
            else if(code == 2) {
                System.out.println("Username " + username + " expired cookie");
                Query.sendResponse(he, "No such cookie / expired cookie", rCode.noSuchCookieOrExpiredCookie.getValue());
            }
            else if(code == 3) {
                System.out.println("Username " + username + " invalid cookie request: " + username + " , " + cookie);
                Query.sendResponse(he, "Invalid cookie request", rCode.invalidCookieRequest.getValue());
            }
            return false;
        }
        System.out.println("Cookie is valid");
        return true;
    }
}
