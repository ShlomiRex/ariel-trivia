import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class SigninHandler implements HttpHandler {
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
        System.out.println("=== SigninHandler ===");
        if(he.getRequestMethod().equals("GET")) {
            Response.sendResponse(he, "Method must be POST", 1);
            return;
        }


        Map<String, List<String>> p = Query.parseBodyQuery(he.getRequestBody());


        List<String> tmp;

        tmp = p.get("username");
        if(tmp == null) {
            Response.sendResponse(he, "Username is missing", 1);
            return;
        }
        String username = (String) tmp.get(0);
        tmp = p.get("password");
        if(tmp == null) {
            Response.sendResponse(he, "Password is missing", 1);
            return;
        }
        String password = (String) tmp.get(0); //Already encrypted


        if(username == null || password == null) {
            Response.sendResponse(he, "Username or password are null", rCode.usernameOrPasswordNull.getValue());
            return;
        }

        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
        MongoCollection<Document> users_col = database.getCollection("users");

        Document doc = users_col.find(eq("username",username)).first();
        if(doc == null) {
            Response.sendResponse(he, "No such username", rCode.noSuchUsername.getValue());
            return;
        }
        String db_pass = (String) doc.get("password");
        mongoClient.close();
        if(db_pass.equals(password)) {
            System.out.println("Successful signin, " + username);
            Cookie.removeCookie(uri, username); //Remove from db
            String cookie = Cookie.getCookie();
            Response.sendResponse(he, cookie, rCode.OK.getValue());
            Cookie.insertCookieDocument(uri, cookie, username);
        } else {
            Response.sendResponse(he, "Password incorrect", rCode.passwordIncorrect.getValue());
        }
    }



}
