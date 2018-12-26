package com.example.root.ariel_trivia_app.base.data_models.trivia;

import android.app.Application;

import com.example.root.ariel_trivia_app.Global;
import com.example.root.ariel_trivia_app.base.data_models.metadata.TriviaMetadata;

import org.bson.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trivia extends Application implements Mappable, Serializable {
    @Id
    private String id;
    private String creator_username;
    private Question question;
    private TriviaMetadata metadata;

    private Forum forum;

    public Trivia(Document d) {
        //id
        this.id = ""+System.currentTimeMillis();
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

        List<String> whoLiked = (List<String>) d.get("whoLiked");
        this.metadata = new TriviaMetadata(whoLiked);

    }

    public Trivia( String question , String answer ,String[] wrongAnswers){
        this.id = ""+System.currentTimeMillis();
        this.creator_username = Global.user.getUsername();
        this.forum = new Forum(this.id, new ArrayList<Comment>());

        int rightAnswer = (int) (Math.random()*4);
        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 0,j = 0; i < 4; i++){
            if(i == rightAnswer){
                answers.add(answer);
            }
            else{
                answers.add( wrongAnswers[j]);
                j ++;
            }
        }
        ArrayList<Integer> difficulty_count = new ArrayList<Integer>();
        difficulty_count.add(0);
        difficulty_count.add(0);
        difficulty_count.add(0);
        difficulty_count.add(0);
        difficulty_count.add(0);
        this.question = new Question(new ArrayList<String>(), question, answers, rightAnswer, difficulty_count,0, 0);

        this.metadata = new TriviaMetadata(new ArrayList<String>());
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
        this.getQuestion().setLikes(this.getQuestion().getLikes() + 1);
    }

    public void rate(int difficulty){
        //TODO
    }

    public void addComment(Comment comment){
        forum.getComments().add(comment);
    }

    @Override
    public org.dizitart.no2.Document write(NitriteMapper mapper) {
        org.dizitart.no2.Document nitd = new org.dizitart.no2.Document();
        nitd.put("id", id);
        nitd.put("creator_username", creator_username);
        nitd.put("tags", question.getTags());
        nitd.put("question", question.getQuestion());
        nitd.put("answers", question.getAnswers());
        nitd.put("correct_answer_index", question.getCorrect_answer_index());
        nitd.put("difficulty_count", question.getDifficulty_count());
        nitd.put("difficulty", question.getDifficulty());
        nitd.put("likes", question.getLikes());
        nitd.put("whoLiked", metadata.getWhoLiked());
        nitd.put("comments", forum.getComments());
        return nitd;
    }

    @Override
    public void read(NitriteMapper mapper, org.dizitart.no2.Document document) {
        this.id = document.get("id").toString();
        this.creator_username = (String) document.get("creator_username");
        String question_str = (String) document.get("question");
        ArrayList<String> tags = (ArrayList<String>) document.get("tags");
        ArrayList<String> answers_arr = (ArrayList<String>) document.get("answers");
        int correct_answer_index = (int) document.get("correct_answer_index");
        ArrayList<Integer> difficulty_count = (ArrayList<Integer>) document.get("difficulty_count");
        double difficulty = (double) document.get("difficulty");
        int likes = (int) document.get("likes");
        List<String> whoLiked = (List<String>) document.get("whoLiked");
        this.metadata = new TriviaMetadata(whoLiked);
        ArrayList<Comment> comments = (ArrayList<Comment>) document.get("comments");

        this.question = new Question(tags, question_str, answers_arr, correct_answer_index, difficulty_count, difficulty, likes);
        this.forum = new Forum(this.id,  comments);
    }

    public TriviaMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(TriviaMetadata metadata) {
        this.metadata = metadata;
    }
}