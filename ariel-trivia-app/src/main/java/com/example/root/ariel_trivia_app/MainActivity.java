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

import org.bson.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    private final String TAG = MainActivity.class.getSimpleName();

    private final LoginInfo testLoginInfo = new LoginInfo("abc", "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final Button btn_login = (Button) findViewById(R.id.login_btn_login);
        final EditText etxt_username = (EditText) findViewById(R.id.login_etxt_username);
        final EditText etxt_password = (EditText) findViewById(R.id.login_etxt_password);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO login
                Intent i = new Intent(MainActivity.this, userOptionsActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.guest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SelectTriviaActivity.class);
                startActivity(i);
            };});

        try {
            initDB();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed to read assets", Toast.LENGTH_SHORT).show();
            finish();
        }

        readTriviasFromDB();
        //readUsersFromDB();
    }

    private void initDB() throws IOException, JSONException {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(Global.SharedPreferences.Files.GLOBAL, Context.MODE_PRIVATE);
        boolean firstTime = sharedPref.getBoolean(Global.SharedPreferences.Keys.FIRST_TIME, true);
        Nitrite db;

        if(firstTime) {
            sharedPref.edit().putBoolean(Global.SharedPreferences.Keys.FIRST_TIME, true); //Changed first time flag
            db = Nitrite.builder().filePath(getFilesDir().getPath() + "/" + Global.DB.DB_FILE).openOrCreate(); //init db

            //Truncate
            for(String colName : db.listCollectionNames()) {
                db.getCollection(colName).drop();
            }

            //insert into db the datasets
            String json_trivias = readAssetsFile("db/trivias.json");
            String json_users = readAssetsFile("db/users.json");
            JSONArray jsonArray;

            ObjectRepository<Trivia> triviasRepository = db.getRepository(Trivia.class);;
            ObjectRepository<LoginInfo> usersRepository = db.getRepository(LoginInfo.class);


            jsonArray = new JSONArray(json_users);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject; jsonObject = jsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                String password_sha256 = jsonObject.getString("password");
                LoginInfo loginInfo = new LoginInfo(username, password_sha256);
                usersRepository.insert(loginInfo);
            }

            jsonArray = new JSONArray(json_trivias);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject; jsonObject = jsonArray.getJSONObject(i);
                Document d = Document.parse(jsonObject.toString());
                Trivia t = new Trivia(d);
                triviasRepository.insert(t);
            }

            db.close();
        } else {

        }


    }

    /**
     * Test read, output in logcat
     */
    private void readTriviasFromDB() {
        String path = getFilesDir().getPath() + "/" + Global.DB.DB_FILE;
        Nitrite db = Nitrite.builder().filePath(path).openOrCreate();
        ObjectRepository<Trivia> repo = db.getRepository(Trivia.class);
        Cursor<Trivia> cursor = repo.find();
        for(Trivia t : cursor) {
            Log.d(TAG, t.toJson());
        }

        db.close();
    }

    /**
     * Test read, output in logcat
     */
    private void readUsersFromDB() {
        String path = getFilesDir().getPath() + "/" + Global.DB.DB_FILE;
        Nitrite db = Nitrite.builder().filePath(path).openOrCreate();
        ObjectRepository<LoginInfo> repo = db.getRepository(LoginInfo.class);
        Cursor<LoginInfo> cursor = repo.find();
        for(LoginInfo l : cursor) {
            Log.d(TAG, "username:"+l.getUsername()+",password:"+l.getPassword_sha256());
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
}
