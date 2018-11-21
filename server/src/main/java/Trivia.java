
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trivia {
    private String question = "";
    private final String[] answers = new String[4];
    private final List<Comment> comments = new ArrayList<>();
    private int correct_answer_index;
    private Document d = null;

    /**
     * temporarly constructor
     * @param difficulty
     */
    public Trivia(int difficulty){
        answers[0] = "no";
        answers[1] = "mabe";
        answers[2] = "yes";
        answers[3] = "(=";
        question = "will we succeed?";
        comments.add(new Comment("arye", "hey!! it worrrrrx!!! (=\n(;"));
        correct_answer_index = 2;
    }
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

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public List<Comment> getComments() {
        return comments;
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
