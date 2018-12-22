package com.example.root.ariel_trivia_app.base;

import java.util.List;

public class TriviaFilter {
    private List<String> labels;
    private int difficulty = -1, likes = -1;
    private Operator difficulty_o, likes_o;

    public enum Operator {
        gt, lt;
    };

    public TriviaFilter() {

    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Operator getDifficulty_o() {
        return difficulty_o;
    }

    public void setDifficulty_o(Operator difficulty_o) {
        this.difficulty_o = difficulty_o;
    }

    public Operator getLikes_o() {
        return likes_o;
    }

    public void setLikes_o(Operator likes_o) {
        this.likes_o = likes_o;
    }


}
