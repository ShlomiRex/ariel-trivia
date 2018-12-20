package com.example.root.ariel_trivia_app;

public class UserQuestion {
    User user;
    Trivia question;
    boolean answerCorrect;
    boolean isLike;
    int rate;

    public UserQuestion(User user, Trivia question) {
        this.user = user;
        this.question = question;
        isLike = false;
        answerCorrect = false;
        rate = -1;
    }

    public void answerd(){
        answerCorrect = true;
    }

    public void changeLike(){
        isLike = true;
    }

    public void setRate(int _rate){
        rate = _rate;
    }
}
