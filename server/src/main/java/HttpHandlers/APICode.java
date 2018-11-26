package HttpHandlers;

public enum APICode {

    signin(1), signup(2), getTrivias(10), uploadTrivia(11), addComment(20);
    private int apicode;

    public int getApicode() {
        return apicode;
    }

    APICode(int code) {
        this.apicode = code;
    }
}
