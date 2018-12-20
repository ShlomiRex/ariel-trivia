package com.example.root.ariel_trivia_app;

public class Rate {
    Trivia question;
    int numOfRaters = 0;
    double counterRate = 3;

    public Rate(Trivia question) {
        this.question = question;
    }

    public Rate(Trivia question, int numOfRaters, double counterRate) {
        this.question = question;
        this.numOfRaters = numOfRaters;
        this.counterRate = counterRate;
    }

    int getRate(){

        return (int)Math.rint(counterRate);
    }
    public void addRate(int difficulty){
        counterRate = (counterRate*numOfRaters + difficulty)/(++numOfRaters);
    }
}
