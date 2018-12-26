package com.example.root.ariel_trivia_app.base.data_models.metadata;

import java.io.Serializable;
import java.util.List;

public class TriviaMetadata implements Serializable {
    private List<String> whoLiked;

    public TriviaMetadata(List<String> whoLiked) {
        this.whoLiked = whoLiked;
    }

    public List<String> getWhoLiked() {
        return whoLiked;
    }

    public void setWhoLiked(List<String> whoLiked) {
        this.whoLiked = whoLiked;
    }
}
