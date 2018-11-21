package HttpHandlers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.*;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class RootHandler implements HttpHandler {
    private String uri;
    public RootHandler(String uri) {
        this.uri = uri;
    }
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println(" === HttpHandlers.RootHandler ===");
        boolean res = isUp(uri);
        String response;
        if(res) {
            response = "Server is up!";
        } else {
            response = "Server is down :(";
        }

        Query.parseBodyQuery(he.getRequestBody());

        InputStreamReader isr =  new InputStreamReader(he.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);

// From now on, the right way of moving from bytes to utf-8 characters:

        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        br.close();
        isr.close();

        System.out.println(buf);



        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    /**
     * Check if server is up or down.
     * @return True if the server works.
     */
    public static boolean isUp(String uri) {
        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
            MongoCollection<Document> d = database.getCollection("test");
            d.count();
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}