package com.example.root.ariel_trivia_app;

import android.app.Application;

import org.bson.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Trivia extends Application implements Mappable {
    private String id;
    private String creator_username;
    private Question question;

    private Forum forum;

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
        this.id = (String) d.get("_id");
        //creator username
        this.creator_username = d.getString("creator_username");
        //tags / labels
        List<String> tags = (List<String>) d.get("tags");
        //question
        String question = d.getString("question");
        //answers
        List<String> answers = (List<String>) d.get("answers");
        String[] answers_array = new String[answers.size()];
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
        ArrayList<Comment> _comments = new ArrayList<>();
        for (Document c : comments)
            _comments.add(new Comment(c.getString("username"), c.getString("message")));

        this.forum = new Forum(this.id, _comments);
        this.question = new Question(tags, question, answers, correct_answer_index, difficulty_count, difficulty, likes);
    }
    public String getCreator_username() {
        return creator_username;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void addComment(String comment){
        //TODO
    }

    @Override
    public org.dizitart.no2.Document write(NitriteMapper mapper) {
        org.dizitart.no2.Document nitd = new org.dizitart.no2.Document();
        nitd.put("creator_username", creator_username);
        nitd.put("tags", question.getTags());
        nitd.put("question", question.getQuestion());
        nitd.put("answers", question.getAnswers());
        nitd.put("correct_answer_index", question.getCorrect_answer_index());
        nitd.put("difficulty_count", question.getDifficulty_count());
        nitd.put("difficulty", question.getDifficulty());
        nitd.put("likes", question.getLikes());
        nitd.put("comments", forum.getComments());
        return nitd;
    }

    @Override
    public void read(NitriteMapper mapper, org.dizitart.no2.Document document) {
        initFromDocument(document);
    }

    public void initFromDocument(org.dizitart.no2.Document document) {

        this.id = document.get("_id").toString();
        this.creator_username = (String) document.get("creator_username");
        String question_str = (String) document.get("question");
        ArrayList<String> tags = (ArrayList<String>) document.get("tags");
        ArrayList<String> answers_arr = (ArrayList<String>) document.get("answers");
        int correct_answer_index = (int) document.get("correct_answer_index");
        ArrayList<Integer> difficulty_count = (ArrayList<Integer>) document.get("difficulty_count");
        double difficulty = (double) document.get("difficulty");
        int likes = (int) document.get("likes");
        ArrayList<Comment> comments = (ArrayList<Comment>) document.get("comments");

        this.question = new Question(tags, question_str, answers_arr, correct_answer_index, difficulty_count, difficulty, likes);
        this.forum = new Forum(this.id,  comments);
    }
}