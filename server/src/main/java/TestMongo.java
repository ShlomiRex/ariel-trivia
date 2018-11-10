import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class TestMongo {
    public static void main(String[] args) {
        MongoClientURI uri = new MongoClientURI("mongodb://Admin:Admin123123@ds049219.mlab.com:49219/ariel-trivia");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("ariel-trivia");

        MongoCollection<Document> col = database.getCollection("users");

        try (MongoCursor<Document> cursor = col.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        }
        mongoClient.close();

    }

    public static void printAllDocs() {
        MongoClientURI uri = new MongoClientURI("mongodb://Admin:Admin123123@ds049219.mlab.com:49219/ariel-trivia");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("ariel-trivia");

        MongoCollection<Document> col = database.getCollection("users");

        try (MongoCursor<Document> cursor = col.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        }
        mongoClient.close();

    }
}
