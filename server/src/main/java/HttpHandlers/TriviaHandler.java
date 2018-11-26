package HttpHandlers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;

public class TriviaHandler implements HttpHandler {
    private String uri;

    public TriviaHandler(String uri) {
        this.uri = uri;
    }


    private enum rCode {
        errParsingQuery(1),OK(200);

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

        //Need multiple inputstream if we read multiple times the same stream!
        //Read MyUtils.cloneInputStream
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(he.getRequestBody().readAllBytes()); //he.getRequestBody() inputstream is now eof => useless
        baos.flush();

        System.out.println("=== HttpHandlers.TriviaHandler ===");


        if(he.getRequestMethod().equals("GET")) {
            System.out.println("=== METHOD: GET ===");
            //Validate cookie
            if (Cookie.validateCookie(he, uri) == false) {
                System.out.println("Validate cookie = false, terminating");
                return;
            }
            String response = "";

            String query = he.getRequestURI().getQuery();
            try {
                Map<String, List<String>> map = Query.parseParametersQuery(query);

                String errbuf = "";

                Bson likes_filter = null;
                proccess_likes: //Very useful
                {
                    //Check likes number
                    if(map.containsKey("likes") == false) {
                        break proccess_likes;
                    }

                    int likes = Integer.parseInt(map.get("likes").get(0));
                    if (likes < 0) {
                        errbuf += "likes less than 0";
                        break proccess_likes;
                    }

                    //Check likes operator
                    if (map.containsKey("likes_o") == false) {
                        errbuf += "Missing likes_o";
                        break proccess_likes;
                    }

                    String likes_o = map.get("likes_o").get(0);
                    if(likes_o.equals("gt") == false && likes_o.equals("lt") == false) {
                        errbuf += "likes operator '" + likes_o + " is not gt or lt";
                        break proccess_likes;
                    }


                    if(likes_o.equals("lt")) {
                        likes_filter = lte("likes", likes);
                    } else {
                        likes_filter = gte("likes", likes);
                    }

                }


                if(errbuf.length() != 0) {
                    System.err.println("Processing likes Errors: \n" + errbuf);
                }
                errbuf = "";

                Bson difficulty_filter = null;
                process_difficulty:
                {
                    //Check difficulty number
                    if (map.containsKey("difficulty") == false) {
                        break process_difficulty;
                    }

                    double difficulty = Double.parseDouble((map.get("difficulty").get(0)));
                    if (difficulty < 0) {
                        errbuf += "difficulty less than 0";
                        break process_difficulty;
                    }

                    //Check difficulty operator
                    if (map.containsKey("difficulty_o") == false) {
                        errbuf += "Missing difficulty_o";
                        break process_difficulty;
                    }

                    String difficulty_o = map.get("difficulty_o").get(0);
                    if (difficulty_o.equals("gt") == false && difficulty_o.equals("lt") == false) {
                        errbuf += "difficulty operator '" + difficulty_o + " is not gt or lt";
                        break process_difficulty;
                    }


                    if (difficulty_o.equals("lt")) {
                        difficulty_filter = lte("difficulty", difficulty);
                    } else {
                        difficulty_filter = gte("difficulty", difficulty);
                    }

                }

                if(errbuf.length() != 0) {
                    System.err.println("Processing difficulty Errors: \n" + errbuf);
                }
                errbuf = "";

                Bson labels_filter = null;
                process_labels: {
                    //db.trivias.find({tags: {$in: ["unix","maths"]} }).pretty()
                    if(map.containsKey("label") == false) {
                        break process_labels;
                    }

                    List<String> labels = map.get("label");
                    labels_filter = Filters.in("tags",labels);
                }
                if(errbuf.length() != 0) {
                    System.err.println("Processing difficulty Errors: \n" + errbuf);
                }
                errbuf = "";

                Bson trivias_filter = null;
                process_filters: {
                    if(difficulty_filter != null && likes_filter != null && labels_filter != null) {
                        trivias_filter = and(and(likes_filter, difficulty_filter), labels_filter);
                    } else if(difficulty_filter != null && likes_filter == null) {
                        if(labels_filter == null) {
                            trivias_filter = difficulty_filter;
                        } else {
                            trivias_filter = and(difficulty_filter, labels_filter);
                        }
                    } else if(difficulty_filter == null && likes_filter != null) {
                        if(labels_filter == null) {
                            trivias_filter = likes_filter;
                        } else {
                            trivias_filter = and(likes_filter, labels_filter);
                        }
                    } else {
                        if(labels_filter == null) {
                            trivias_filter = null;
                        } else {
                            trivias_filter = labels_filter;
                        }
                    }
                }


                MongoClientURI mongoClientURI = new MongoClientURI(uri);
                MongoClient mongoClient = new MongoClient(mongoClientURI);
                MongoDatabase database = mongoClient.getDatabase("ariel-trivia");
                MongoCollection<Document> d = database.getCollection("trivias");

                FindIterable<Document> itr;
                if(trivias_filter != null)
                    itr = d.find(trivias_filter);
                else
                    itr = d.find();
                ArrayList<Document> lst = new ArrayList<>();
                itr.into(lst);
                mongoClient.close();

                for(Document d2 : lst) {
                    response += d2.toJson() + "\n";
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                Response.sendResponse(he, "Error processing query", rCode.errParsingQuery.getValue());
                return;
            }

            Response.sendResponse(he, response, 200);
        } else {
            System.out.println("=== METHOD: POST ===");

            //Validate cookie
            if (Cookie.validateCookie(he, uri) == false) {
                System.out.println("Validate cookie = false, terminating");
                return;
            }
            String response = "";

            String query = he.getRequestURI().getQuery();

            String in = baos.toString();
            System.out.println(in);
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
