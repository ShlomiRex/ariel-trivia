package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Context;

import com.google.common.hash.Hashing;

import org.dizitart.no2.Nitrite;

import java.nio.charset.StandardCharsets;

public class Global {
    public static APIRequests apiRequests;
    public static class SharedPreferences {
        public static class Keys {
            public static final String FIRST_TIME = "isFirstTime";
        }

        public static class Files {
            public static final String GLOBAL = "global";
        }
    }

    public static class DB {
        public static final String DB_FILE = "app.db";
    }


    public static String sha256(String data) {
        return Hashing.sha256().hashString(data, StandardCharsets.UTF_8).toString();
    }
}
