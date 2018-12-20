package com.example.root.ariel_trivia_app;

import com.example.root.ariel_trivia_app.Comment;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Forum {
    private Trivia trivia;
    private ObjectId trivia_id;
    private List<Comment> comments;

    public Forum(Trivia trivia){
        this.trivia = trivia;
        //TODO get object id somehow
        comments = new ArrayList<>();
    }

    public Forum(ObjectId trivia_id, List<Comment> comments) {
        this.trivia_id = trivia_id;
        this.comments = comments;
    }

    public ObjectId getTrivia_id() {
        return trivia_id;
    }

    public void setTrivia_id(ObjectId trivia_id) {
        this.trivia_id = trivia_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(String comment){
        //TODO put real userName
        //TODO add the comment to the user that generate it
        String userName = "blabla";
        Comment newCom = new Comment(this, userName, comment);
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
