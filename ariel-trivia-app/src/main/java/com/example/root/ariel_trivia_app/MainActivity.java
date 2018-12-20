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
    private Nitrite db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        String path = getApplicationContext().getFilesDir().getPath() + "/" + Global.DB.DB_FILE;
        db = Nitrite.builder().filePath(path).openOrCreate();

        final Button btn_login = (Button) findViewById(R.id.login_btn_login);
        final EditText etxt_username = (EditText) findViewById(R.id.login_etxt_username);
        final EditText etxt_password = (EditText) findViewById(R.id.login_etxt_password);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO login
                String username = etxt_username.getText().toString();
                String password_plain = etxt_password.getText().toString();
                String password_sha256 = Global.sha256(password_plain);
                LoginInfo loginInfo = new LoginInfo(username, password_sha256);

                Global.apiRequests = new APIRequests(db, loginInfo);
                if(Global.apiRequests.signin() == true) {
                    Intent i = new Intent(MainActivity.this, userOptionsActivity.class);
                    startActivity(i);
                    Global.apiRequests = null;
                } else {
                    Toast.makeText(getApplicationContext(), "Usename / password incorrect!", Toast.LENGTH_LONG).show();
                }
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
            readTriviasFromDB(); //For testing
            readUsersFromDB(); //For testing
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

            //Truncate anyway
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
        } else {
            Log.d(TAG, "NOT First Time :(");
        }


    }

    /**
     * Test read, output in logcat
     */
    private void readTriviasFromDB() {
        ObjectRepository<Trivia> repo = db.getRepository(Trivia.class);
        Cursor<Trivia> cursor = repo.find();
        for(Trivia t : cursor) {
            Log.d(TAG, t.getQuestion().getQuestion());
        }
    }

    /**
     * Test read, output in logcat
     */
    private void readUsersFromDB() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();;
        db.close();
    }
}
