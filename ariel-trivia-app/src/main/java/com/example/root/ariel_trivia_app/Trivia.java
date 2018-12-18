package com.example.root.ariel_trivia_app;

import android.app.Application;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trivia extends Application {
    private ObjectId id;
    private String creator_username;
    private Question question;
    private final List<Comment> comments = new ArrayList<>();
    private Document d; //The document object of db

    private Forum forum;

    public static void main(String[] args) {
        Trivia trivia = new Trivia();
        System.out.println(trivia.toString());
        System.out.println("\n\n\n\n");
        System.out.println(trivia.toJson());
    }

    public Trivia() {
        this(        Document.parse("{\n" +
                "\t\"_id\" : ObjectId(\"5be9e1eeb5bb3f88c77eba18\"),\n" +
                "\t\"creator_username\" : \"vgtvgy1\",\n" +
                "\t\"tags\" : [\n" +
                "\t\t\"easy question\",\n" +
                "\t\t\"maths\",\n" +
                "\t\t\"for kids\"\n" +
                "\t],\n" +
                "\t\"question\" : \"How much is 2+2?\",\n" +
                "\t\"answers\" : [\n" +
                "\t\t\"25\",\n" +
                "\t\t\"4\",\n" +
                "\t\t\"I don't know\",\n" +
                "\t\t\"3.14\"\n" +
                "\t],\n" +
                "\t\"correct_answer_index\" : 1,\n" +
                "\t\"difficulty_count\" : [\n" +
                "\t\t5,\n" +
                "\t\t0,\n" +
                "\t\t2,\n" +
                "\t\t0,\n" +
                "\t\t2\n" +
                "\t],\n" +
                "\t\"difficulty\" : 2.3,\n" +
                "\t\"likes\" : 3,\n" +
                "\t\"comments\" : [\n" +
                "\t\t{\n" +
                "\t\t\t\"username\" : \"vgtvgy1\",\n" +
                "\t\t\t\"message\" : \"I like this trivia question\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"username\" : \"abc\",\n" +
                "\t\t\t\"message\" : \"Hey Shlomi, stop spamming!\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}\n"));
    }

    public Trivia(Document d) {
        //id
        this.id = (ObjectId) d.get("_id");
        //creator username
        this.creator_username = d.getString("creator_username");
        //tags / labels
        List<String> tags = (List<String>) d.get("tags");
        //question
        String question = d.getString("question");
        //answers
        List<String> answers = (List<String>) d.get("answers");
        String[] answers_array = new String[Question.ANSWERS];
        for(int i = 0; i < answers_array.length; i++) {
            answers_array[i] = answers.get(i);
        }
        //answer index
        int correct_answer_index = d.getInteger("correct_answer_index").intValue();
        //difficulty count
        List<Integer> difficulty_count = (List<Integer>) d.get("difficulty_count");
        int[] difficulty_count_array = new int[Question.DIFF_COUNT];
        for(int i = 0; i < difficulty_count_array.length; i++) {
            difficulty_count_array[i] = difficulty_count.get(i).intValue();
        }
        //difficulty
        double difficulty = d.getDouble("difficulty");
        //likes
        int likes = d.getInteger("likes").intValue();
        //comments
        List<Document> comments = (List<Document>) d.get("comments");
        for (Document c : comments)
            this.comments.add(new Comment(c.getString("username"), c.getString("message")));
        //document
        this.d = d;

        this.forum = new Forum(this.id, this.comments);
        this.question = new Question(tags, question, answers_array, correct_answer_index, difficulty_count_array, difficulty, likes);
    }


    /**
     * Use this instead of toString its better :D
     * @return
     */
    public String toJson() {
        return d.toJson();
    }

    public String getCreator_username() {
        return creator_username;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
    }

    public Document getD() {
        return d;
    }

    public void setD(Document d) {
        this.d = d;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public void addLike(){
        //TODO
    }

    public void rate(int difficulty){
        //TODO
    }
}