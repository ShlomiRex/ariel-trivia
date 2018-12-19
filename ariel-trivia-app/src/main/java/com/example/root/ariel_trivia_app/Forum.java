package com.example.root.ariel_trivia_app;

import com.example.root.ariel_trivia_app.Comment;

import org.bson.types.ObjectId;

import java.util.List;

public class Forum {
    private String trivia_id;
    private List<Comment> comments;

    public Forum(String trivia_id, List<Comment> comments) {
        this.trivia_id = trivia_id;
        this.comments = comments;
    }

    @Override
    public String toString()  {
        String allForum = "";
        for(Comment comment:comments){
            allForum += "\n----------------\n" + comment;
        }
        return allForum;
    }
}
