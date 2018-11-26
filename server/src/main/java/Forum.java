import org.bson.types.ObjectId;

import java.util.List;

public class Forum {
    private ObjectId trivia_id;
    private List<Comment> comments;

    public Forum(ObjectId trivia_id, List<Comment> comments) {
        this.trivia_id = trivia_id;
        this.comments = comments;
    }

    public ObjectId getTrivia_id() {
        return trivia_id;
    }

    public void setTrivia_id(ObjectId trivia_id) {
        this.trivia_id = trivia_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
