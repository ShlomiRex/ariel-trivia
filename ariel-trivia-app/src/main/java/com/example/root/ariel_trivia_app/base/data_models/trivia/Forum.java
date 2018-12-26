package com.example.root.ariel_trivia_app.base.data_models.trivia;

import java.io.Serializable;
import java.util.List;

public class Forum implements Serializable {
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

    public String getTrivia_id() {
        return trivia_id;
    }

    public void setTrivia_id(String trivia_id) {
        this.trivia_id = trivia_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
