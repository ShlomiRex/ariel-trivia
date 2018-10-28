package com.example.root.ariel_trivia_app;

import android.util.Pair;

import com.mongodb.util.JSON;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Trivia {
    private final String[] answers = new String[4];
    private final List<Comment> comments = new ArrayList<>();
    private final int correct_answer_index;
    private final Document d;

    public Trivia(Document d) {
        this.d = d;
        List<String> answers = (List<String>) d.get("answers");
        for(int i = 0; i < 4; i++)
            this.answers[i] = answers.get(i);

        correct_answer_index = d.getInteger("correct_answer_index").intValue();

        List<Document> comments = (List<Document>) d.get("comments");
        for(Document c : comments)
            this.comments.add(new Comment(c.getString("username"),c.getString("message")));
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


    @Override
    public String toString() {
        return "Trivia{" +
                "answers=" + Arrays.toString(answers) +
                ", comments=" + comments +
                ", correct_answer_index=" + correct_answer_index +
                ", d=" + d +
                '}';
    }
}
