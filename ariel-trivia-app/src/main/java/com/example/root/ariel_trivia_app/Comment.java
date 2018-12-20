package com.example.root.ariel_trivia_app;

import java.io.Serializable;

public class Comment implements Serializable {
    private final String username, message;

    Forum forum;
    String time;

    public Comment(Forum forum, String username, String message) {
        this.username = username;
        this.message = message;
        this.forum = forum;
        //TODO make time
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return this.username + "\n\n" + this.message;
    }
}
