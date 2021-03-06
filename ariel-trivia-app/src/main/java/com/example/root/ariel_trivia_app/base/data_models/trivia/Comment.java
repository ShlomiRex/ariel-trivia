package com.example.root.ariel_trivia_app.base.data_models.trivia;

import java.io.Serializable;

public class Comment implements Serializable {
    private final String username, message;

    public Comment(String username, String message) {
        this.username = username;
        this.message = message;
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
