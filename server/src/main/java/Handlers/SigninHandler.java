package Handlers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;

import java.io.IOException;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class SigninHandler extends MyMongoHandler implements HttpHandler {
    private String uri;
    public SigninHandler(String uri) {
        this.uri = uri;
    }

    private enum rCode {
        usernameOrPasswordNull(1), noSuchUsername(2), passwordIncorrect(3), unknown(4),OK(200);

        private final int value;
        private rCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    };

    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param he the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("signin()");
        Map<String, Object> p = parseBodyQuery(he);
        String username = (String) p.get("username");
        String password = (String) p.get("password"); //Already encrypted

        if(username == null || password == null) {
            sendResponse(he, null, rCode.usernameOrPasswordNull.getValue());
            return;
        }

        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
        MongoCollection<Document> users_col = database.getCollection("users");

        Document doc = users_col.find(eq("username",username)).first();
        if(doc == null) {
            sendResponse(he, null, rCode.noSuchUsername.getValue());
            return;
        }
        String db_pass = (String) doc.get("password");
        mongoClient.close();
        if(db_pass.equals(password)) {
            sendResponse(he, null, rCode.OK.getValue());
        } else {
            sendResponse(he, null, rCode.passwordIncorrect.getValue());
        }
        System.out.println("signin() end");
    }
}
