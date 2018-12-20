package com.example.root.ariel_trivia_app;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    public static final int ANSWERS = 4; //How much answers there are in 1 trivia
    private List<String> tags; // "labels"
    private String question;
    private final String[] answers = new String[4];
    private int correct_answer_index;



    public Question(List<String> tags, String question, String[] answers, int correct_answer_index) {
        this.tags = tags;
        this.question = question;
        for(int i = 0; i < ANSWERS; i++) {
            this.answers[i] = answers[i];
        }
        this.correct_answer_index = correct_answer_index;
    }

    public Question(String question, int correct_answer_index, String[] answers) {
        this.question = question;
        this.correct_answer_index = correct_answer_index;
        for(int i = 0; i < ANSWERS; i++) {
            this.answers[i] = answers[i];
        }
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


}
