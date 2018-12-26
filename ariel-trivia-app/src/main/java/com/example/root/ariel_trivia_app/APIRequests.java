package com.example.root.ariel_trivia_app;

import android.util.Log;

import com.example.root.ariel_trivia_app.base.data_models.trivia.Comment;
import com.example.root.ariel_trivia_app.base.data_models.user.LoginInfo;
import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;
import com.example.root.ariel_trivia_app.base.TriviaFilter;
import com.example.root.ariel_trivia_app.base.data_models.user.User;

import org.dizitart.no2.FindOptions;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;

public class APIRequests {

    private String cookie;
    private LoginInfo loginInfo;
    private Nitrite db;
    private ObjectRepository<Trivia> triviaObjectRepository;
    private ObjectRepository<User> userObjectRepository;
    public static final String TAG = APIRequests.class.getSimpleName();
    public APIRequests(Nitrite db, LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
        this.db = db;
        this.triviaObjectRepository = db.getRepository(Trivia.class);
        this.userObjectRepository = db.getRepository(User.class);
    }

    /**
     * @return True if success
     * Sets Global.user
     */
    public boolean signin() {
        List<User> users = userObjectRepository.find(ObjectFilters.eq("username", loginInfo.getUsername())).toList();
        if(users.size() > 1) {
            Log.e(TAG, "double users with same username");
            System.exit(1);
        }
        if(users.size() == 0) {
            return false;
        }
        User user = users.get(0);
        if(user.getLoginInfo().getPassword_sha256().toUpperCase().equals(loginInfo.getPassword_sha256().toUpperCase())) {
                boolean isAdmin = user.isAdmin();
                if(isAdmin)
                    Global.user.setAdmin(true);
                else
                    Global.user.setAdmin(false);

                Global.user.setSignedIn(true);
            return true;
        } else {
            return false;
        }
//        NitriteCollection nc = db.getRepository(LoginInfo.class).getDocumentCollection();
//        Document doc = nc.find(and(eq("username", loginInfo.getUsername()), eq("password", loginInfo.getPassword_sha256().toUpperCase()))).firstOrDefault();
//        if(doc == null) {
//            return false;
//        }
//        Global.user.setSignedIn(true);
//        return true;
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
//            return triviaObjectRepository.find().toList(); //TODO: Change
        } else {
            if(limit < 0) {
                return triviaObjectRepository.find().toList();
            } else {
                return triviaObjectRepository.find(FindOptions.limit(0, limit)).toList();
            }
        }

        return triviaObjectRepository.find(FindOptions.limit(0, limit)).toList();
    }

    /**
     * Uploads to database trivia object
     * @param trivia
     */
    public void uploadTrivia(Trivia trivia) {
        triviaObjectRepository.insert(trivia);
    }

    //Adds comment to Trivia AND database.
    public void postComment(Trivia trivia, Comment comment) {
        trivia.getForum().getComments().add(comment);
        triviaObjectRepository.update(trivia);
    }

    /**
     * Register
     * @return True if success.
     */
    public static boolean register(Nitrite db, User user) {
        if(user == null)
            return false;
        ObjectRepository<User> repo = db.getRepository(User.class);
        List<User> userList = repo.find(ObjectFilters.eq("username", user.getLoginInfo().getUsername())).toList();
        if(userList.size() != 0) {
            return false;
        }
        else {
            repo.insert(user);
            return true;
        }
//
//        NitriteCollection nc = userObjectRepository.getDocumentCollection();
//        Document d = nc.find(eq("username", registerInfo.getUsername())).firstOrDefault();
//        if(d == null) {
//            WriteResult a = nc.insert(registerInfo.toDocument());
//            if(a.getAffectedCount() == 0) {
//                return false;
//            }
//            return true;
//        } else {
//            //Username already exists
//            return false;
//        }
    }

    public Trivia getTrivia(String id) {
        ObjectRepository<Trivia> repo = triviaObjectRepository;
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

    /**
     * Checks if ApplicationUser liked this trivia or not and if not, like it and update it in the database.
     * @param trivia
     * @return True if success. False if user already liked it
     */
    public boolean likeTrivia(Trivia trivia) {
        if (trivia.getMetadata().getWhoLiked().contains(Global.user.getUsername())) {
            return false;
        }
        trivia.addLike();
        trivia.getMetadata().getWhoLiked().add(Global.user.getUsername());
        triviaObjectRepository.update(trivia);
        return true;
    }

}
