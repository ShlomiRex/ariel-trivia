package com.example.root.ariel_trivia_app;

import android.util.Log;

import com.example.root.ariel_trivia_app.base.Comment;
import com.example.root.ariel_trivia_app.base.LoginInfo;
import com.example.root.ariel_trivia_app.base.Trivia;
import com.example.root.ariel_trivia_app.base.TriviaFilter;

import org.dizitart.no2.Document;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dizitart.no2.filters.Filters.and;
import static org.dizitart.no2.filters.Filters.eq;

public class APIRequests {

    private String cookie;
    private LoginInfo loginInfo;
    private Nitrite db;
    public static final String TAG = APIRequests.class.getSimpleName();
    public APIRequests(Nitrite db, LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
        this.db = db;
    }

    public List<Comment> getForum(Trivia t) {
        return null;
    }
//
//    public static void main(String[] args) {
//        APIRequests apiRequests = new APIRequests("localhost", 80, "abc", "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD");
//        Trivia trivia = new Trivia();
//        apiRequests.uploadTrivia(trivia);
//    }
//
//    public static void Test2() {
//        String pass_sha256 = Hashing.sha256().hashString("ShlomiABC", StandardCharsets.UTF_8).toString();
//        APIRequests.signup("localhost", 80, "ShlomiABC", pass_sha256);
//    }
//
//    //Works
//    public static void Test1() {
//        TriviaFilter filter = new TriviaFilter();
//        filter.setDifficulty(4);
//        filter.setDifficulty_o(TriviaFilter.Operator.gt);
//        filter.setLabels(Arrays.asList("unix","maths"));
//
//        APIRequests apiRequests = new APIRequests("localhost", 80, "abc", "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD");
//        apiRequests.signin();
//        apiRequests.requestTrivias(filter);
//    }

    /**
     * @return True if success
     */
    public boolean signin() {
        NitriteCollection nc = db.getRepository(LoginInfo.class).getDocumentCollection();
        Document doc = nc.find(and(eq("username", loginInfo.getUsername()), eq("password", loginInfo.getPassword_sha256().toUpperCase()))).firstOrDefault();
        if(doc == null) {
            return false;
        }
        Global.isSignedIn = true;
        return true;
    }

//    /**
//     * Singup.
//     * @param hostname
//     * @param port
//     * @param username
//     * @param password The password in SHA256.
//     */
//    public static void signup(String hostname, int port, String username, String password) {
//
//        Map<String, List<String>> form_data = new HashMap<>();
//        form_data.put("username", Arrays.asList(username));
//        form_data.put("password", Arrays.asList(password));
//        OutputStream out = new ByteArrayOutputStream();
//        try {
//            int status_code = ServerConnector.POST(hostname, port, "signup", null, form_data, out, APICode.signup);
//
//            System.out.println("=== Signup Response Start ===");
//            System.out.println(out.toString());
//
//            System.out.println("Status code: " + status_code);
//            System.out.println("=== Signup Response End ===");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public List<Trivia> requestTrivias(TriviaFilter filter) {
        return requestTrivias(filter, -1);
    }

    public List<Trivia> requestTrivias(TriviaFilter filter, int limit) {
        if(filter != null) {
//            Map<String, List<String>> param_data = new HashMap<>();
//
//            int difficulty = filter.getDifficulty();
//            TriviaFilter.Operator difficulty_o = filter.getDifficulty_o();
//
//            int likes = filter.getLikes();
//            TriviaFilter.Operator likes_o = filter.getLikes_o();
//
//            List<String> labels = filter.getLabels();
//            return db.getRepository(Trivia.class).find().toList(); //TODO: Change
        } else {
            if(limit < 0) {
                return db.getRepository(Trivia.class).find().toList();
            } else {
                return db.getRepository(Trivia.class).find(FindOptions.limit(0, limit)).toList();
            }
        }

        return db.getRepository(Trivia.class).find(FindOptions.limit(0, limit)).toList();
    }

    /**
     * Uploads to database trivia object
     * @param trivia
     */
    public void uploadTrivia(Trivia trivia) {

    }
    /**
     * Uploads to database trivia object
     * @param json
     */
    public void uploadTrivia(String json) {

//        Map<String, List<String>> param_data = new HashMap<>();
//        Map<String, List<String>> form_data = new HashMap<>();
//
//        param_data.put("username", Arrays.asList(username));
//        param_data.put("cookie", Arrays.asList(cookie));
//
//        form_data.put("trivia", Arrays.asList(json));
//
//        OutputStream out = new ByteArrayOutputStream();
//        try {
//            int status_code = ServerConnector.POST(hostname, port, "trivias", param_data, form_data, out, APICode.uploadTrivia);
//            System.out.println("=== uploadTrivia Response Start ===");
//            System.out.println(out.toString());
//            System.out.println("Status code: " + status_code);
//            System.out.println("=== uploadTrivia Response End ===");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //Adds comment to Trivia AND database.
    public void postComment(Trivia trivia, Comment comment) {
        trivia.getForum().getComments().add(comment);
//        ObjectRepository<Trivia> repo =  db.getRepository(Trivia.class); //TODO: How to filter Object repository? What is "field" of object?
//        List<Trivia> trivias = repo.find().toList();
//        for(Trivia t : trivias) {
//            if(t.getId().equals(trivia.getId())) {
//                t.addComment(comment);
//                repo.update(t);
//            }
//        }
    }

    /**
     * Register.
     * @param registerInfo
     * @return True if success.
     */
    public static boolean register(Nitrite db, LoginInfo registerInfo) {
        NitriteCollection nc = db.getRepository(LoginInfo.class).getDocumentCollection();
        Document d = nc.find(eq("username", registerInfo.getUsername())).firstOrDefault();
        if(d == null) {
            WriteResult a = nc.insert(registerInfo.toDocument());
            if(a.getAffectedCount() == 0) {
                return false;
            }
            return true;
        } else {
            //Username already exists
            return false;
        }
    }

    public Trivia getTrivia(String id) {
        ObjectRepository<Trivia> repo = db.getRepository(Trivia.class);
        List<Trivia> result = repo.find(ObjectFilters.eq("id", id)).toList();
        if(result.size() < 1) {
            return null;
        }
        return result.get(0);


//        List<Trivia> trivias = repo.find(ObjectFilters.eq("_id", id)).toList();
//        if(trivias.size() == 0 || trivias.size() > 1) {
//            return null;
//        }
        //return trivias.get(0);
    }
}
