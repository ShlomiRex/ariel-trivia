package com.example.root.ariel_trivia_app;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.ArrayList;
import java.util.List;

import static org.dizitart.no2.filters.Filters.and;
import static org.dizitart.no2.filters.Filters.eq;

public class APIRequests {

    private String cookie;
    private LoginInfo loginInfo;
    private Nitrite db;

    private boolean isSignedIn =false;

    public APIRequests(Nitrite db, LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
        this.db = db;
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
        isSignedIn = true;
        return true;

    }

    /**
     * Singup.
     * @param hostname
     * @param port
     * @param username
     * @param password The password in SHA256.
     */
    public static void signup(String hostname, int port, String username, String password) {

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
    }

    /**
     * GET Method
     * @param filter
     * @return
     */
    public List<Trivia> requestTrivias(TriviaFilter filter) {
        List<Trivia> result = new ArrayList<>();

//        try {
//            Map<String, List<String>> param_data = new HashMap<>();
//
//            int difficulty = filter.getDifficulty();
//            TriviaFilter.Operator difficulty_o = filter.getDifficulty_o();
//
//            int likes = filter.getLikes();
//            TriviaFilter.Operator likes_o = filter.getLikes_o();
//
//            List<String> labels = filter.getLabels();
//
//
//            param_data.put("username", Arrays.asList(username));
//            param_data.put("cookie", Arrays.asList(cookie));
//
//            if(likes != -1 && likes_o != null) {
//                param_data.put("likes", Arrays.asList(""+likes));
//                param_data.put("likes_o", Arrays.asList(likes_o.name()));
//            }
//
//
//            if(difficulty != -1 && difficulty_o != null) {
//                param_data.put("difficulty", Arrays.asList(""+difficulty));
//                param_data.put("difficulty_o", Arrays.asList(difficulty_o.name()));
//            }
//
//            if(labels != null) {
//                List<String> lbl_values = new ArrayList<>();
//                for(String lbl : labels) {
//                    lbl_values.add(lbl);
//                }
//                param_data.put("label", lbl_values);
//            }
//
//            OutputStream response = new ByteArrayOutputStream();
//            int status_code = ServerConnector.GET(hostname, port, "trivias", param_data, response, APICode.getTrivias);
//
//            System.out.println("=== requestTrivia Response Start ===");
//            System.out.println(response.toString());
//
//            System.out.println("Status code: " + status_code);
//            System.out.println("=== requestTrivia Response End ===");
//
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }

    /**
     * Uploads to database trivia object
     * @param trivia
     */
    public void uploadTrivia(Trivia trivia) {

    }

    public static void uploadTrivia(String question, String answer, String[] wrongAnswers){
        //TODO
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
}
