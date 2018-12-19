package com.example.root.ariel_trivia_app;

import android.app.Activity;
import android.content.Context;

import org.dizitart.no2.Nitrite;

public class Global {
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


    public static Nitrite getDatabase(Context context) {
        String path = context.getFilesDir().getPath() + "/" + Global.DB.DB_FILE;
        Nitrite db = Nitrite.builder().filePath(path).openOrCreate();
        return db;
    }
}
