package com.example.root.ariel_trivia_app;

public class LoginInfo {
    private String username, password_sha256;
    public LoginInfo(String username, String password_sha256) {
        this.username = username;
        this.password_sha256 = password_sha256;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_sha256() {
        return password_sha256;
    }

    public void setPassword_sha256(String password_sha256) {
        this.password_sha256 = password_sha256;
    }
}
