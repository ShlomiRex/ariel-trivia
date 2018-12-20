package com.example.root.ariel_trivia_app;

import android.content.Intent;

import org.bson.types.ObjectId;

import java.util.LinkedList;

public class User {
    private ObjectId ID;
    String name;
    String cookies;
    Boolean isManager;
    LinkedList<UserQuestion> myQuestion;
    public User(String _name, String password){
        name = _name;
        myQuestion = new LinkedList<UserQuestion>();
        ID = APIRequests.signUp(this, password);
    }
    public static boolean login(String name, String password){
        //TODO remember somehow that loged in
        return APIRequests.signin(name, password);
    }
    public void disconnect(){
        //TODO remember some how that disconnected
    }
}
