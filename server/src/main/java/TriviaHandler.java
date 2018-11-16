import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TriviaHandler implements HttpHandler {
    private String uri;

    public TriviaHandler(String uri) {
        this.uri = uri;
    }


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

        //Need multiple inputstream if we read multiple times the same stream!
        //Read MyUtils.cloneInputStream
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(he.getRequestBody().readAllBytes()); //he.getRequestBody() inputstream is now eof => useless
        baos.flush();

        System.out.println("=== TriviaHandler ===");




        if(he.getRequestMethod().equals("GET")) {
            System.out.println("=== METHOD: GET ===");
            //Validate cookie
            if (Cookie.validateCookie(he, uri) == false) {
                System.out.println("Validate cookie = false, terminating");
                return;
            }
            ArrayList<Document> trivias = getAllTrivias(uri);

            String response = "";
            for (Document d : trivias) {
                response += d.toJson() + "\n";
            }

            Response.sendResponse(he, response, 200);
        } else {
            System.out.println("=== METHOD: POST ===");
        }
    }
    /**
     *
     * @return Array of JSONObjects that represents trivias. null if error occured.
     */
    public static ArrayList<Document> getAllTrivias(String uri) {

        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> d = database.getCollection("trivias");

            FindIterable<Document> itr = d.find();
            ArrayList<Document> lst = new ArrayList<>();
            itr.into(lst);

            mongoClient.close();
            return lst;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
