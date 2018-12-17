package com.example.root.ariel_trivia_app;

import java.util.List;

public class Question {
    public static final int ANSWERS = 4; //How much answers there are in 1 trivia
    public static final int DIFF_COUNT = 5; //Size of array of counts of difficulty
    private List<String> tags; // "labels"
    private String question;
    private final String[] answers = new String[4];
    private int correct_answer_index;
    private int[] difficulty_count = new int[5]; //difficulty 1-5
    private double difficulty;
    private int likes;

    public Question(List<String> tags, String question, String[] answers, int correct_answer_index, int[] difficulty_count, double difficulty, int likes) {
        this.tags = tags;
        this.question = question;
        for(int i = 0; i < ANSWERS; i++) {
            this.answers[i] = answers[i];
        }
        this.correct_answer_index = correct_answer_index;
        this.difficulty_count = difficulty_count;
        this.difficulty = difficulty;
        this.likes = likes;
    }


    /**
     *
     * @param answer_index
     * @return True if the answer is correct. False is not.
     */
    public boolean answerQuestion(int answer_index) {
        if(answer_index == correct_answer_index) {
            return true;
        }
        else
            return false;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrect_answer_index() {
        return correct_answer_index;
    }

    public void setCorrect_answer_index(int correct_answer_index) {
        this.correct_answer_index = correct_answer_index;
    }

    public int[] getDifficulty_count() {
        return difficulty_count;
    }

    public void setDifficulty_count(int[] difficulty_count) {
        this.difficulty_count = difficulty_count;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
