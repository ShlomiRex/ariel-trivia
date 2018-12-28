package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.ariel_trivia_app.base.ApplicationUser;
import com.example.root.ariel_trivia_app.base.data_models.user.LoginInfo;
import com.example.root.ariel_trivia_app.base.data_models.trivia.Trivia;
import com.example.root.ariel_trivia_app.base.data_models.user.User;

import org.bson.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends Activity {
    private final String TAG = LoginActivity.class.getSimpleName();
    private Nitrite db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String path = getApplicationContext().getFilesDir().getPath() + "/" + Global.DB_FILE;
        db = Nitrite.builder().filePath(path).openOrCreate();
        //Set global database
        Global.database = db;
        final Button btn_login = (Button) findViewById(R.id.login_btn_login);
        final EditText etxt_username = (EditText) findViewById(R.id.login_etxt_username);
        final EditText etxt_password = (EditText) findViewById(R.id.login_etxt_password);

        final Button btn_register = findViewById(R.id.login_btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        final Button btn_guest = findViewById(R.id.guest);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etxt_username.getText().toString();
                String password_plain = etxt_password.getText().toString();
                String password_sha256 = Global.sha256(password_plain);
                LoginInfo loginInfo = new LoginInfo(username, password_sha256);

                Global.apiRequests = new APIRequests(db, loginInfo);
                Global.user = new ApplicationUser(username);
                if(Global.apiRequests.signin() == true) {
                    //Success login
                    Intent i = new Intent(LoginActivity.this, AfterLoginActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.loginActivity_etxt_incorrect_values, Toast.LENGTH_LONG).show();
                    Global.apiRequests = null;
                    Global.user = null;
                }
            }
        });

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.apiRequests = new APIRequests(db, null);
                Global.apiRequests.setGuest();
                Intent i = new Intent(LoginActivity.this, SelectTriviaActivity.class);
                startActivity(i);
            };});

        try {
            initDB();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
            System.exit(-1);
        }
    }

    private void initDB() throws IOException, JSONException {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(Global.SharedPreferences.Files.GLOBAL, Context.MODE_PRIVATE);
        boolean firstTime = sharedPref.getBoolean(Global.SharedPreferences.Keys.FIRST_TIME, true);

        if(firstTime) {
            Log.d(TAG, "First Time :D");
            SharedPreferences.Editor speditor = sharedPref.edit();
            speditor.putBoolean(Global.SharedPreferences.Keys.FIRST_TIME, false); //Changed first time flag
            speditor.commit();

            //insert into db the datasets
            String json_trivias = readAssetsFile("db/trivias.json");
            String json_users = readAssetsFile("db/users.json");
            JSONArray jsonArray;

            ObjectRepository<Trivia> triviasRepository = db.getRepository(Trivia.class);;
            ObjectRepository<User> usersRepository = db.getRepository(User.class);


            jsonArray = new JSONArray(json_users);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject; jsonObject = jsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                String password_sha256 = jsonObject.getString("password");
                boolean isAdmin = jsonObject.getBoolean("isAdmin");
                LoginInfo loginInfo = new LoginInfo(username, password_sha256);
                User user = new User(loginInfo, isAdmin);
                usersRepository.insert(user);
            }

            jsonArray = new JSONArray(json_trivias);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject; jsonObject = jsonArray.getJSONObject(i);
                Document d = Document.parse(jsonObject.toString());
                Trivia t = new Trivia(d);
                triviasRepository.insert(t);
            }
        } else {
            Log.d(TAG, "NOT First Time :(");
        }


    }

    private String readAssetsFile(String path) throws IOException {
        StringBuilder buf=new StringBuilder();
        InputStream json=getAssets().open(path);
        BufferedReader in=
                new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String str;

        while ((str=in.readLine()) != null) {
            buf.append(str);
        }

        in.close();
        return buf.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();;
        db.close();
    }
}
