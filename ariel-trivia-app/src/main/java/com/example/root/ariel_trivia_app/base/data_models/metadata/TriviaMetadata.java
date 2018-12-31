package com.example.root.ariel_trivia_app.base.data_models.metadata;

import java.io.Serializable;
import java.util.List;

public class TriviaMetadata implements Serializable {
    private List<String> whoLiked;

    public List<String> getWhoRated() {
        return whoRated;
    }

    public void setWhoRated(List<String> whoRated) {
        this.whoRated = whoRated;
    }

    private List<String> whoRated;

    public TriviaMetadata(List<String> whoLiked, List<String> whoRated) {
        this.whoLiked = whoLiked;
        this.whoRated = whoRated;
    }

    public List<String> getWhoLiked() {
        return whoLiked;
    }

    public void setWhoLiked(List<String> whoLiked) {
        this.whoLiked = whoLiked;
    }
}
