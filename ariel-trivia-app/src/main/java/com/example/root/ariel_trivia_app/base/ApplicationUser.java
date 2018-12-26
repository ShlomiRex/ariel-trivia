package com.example.root.ariel_trivia_app.base;

public class ApplicationUser {
    private String username;
    private boolean isSignedIn;
    private boolean isGuest;
    private boolean isAdmin;

    private ApplicationUser(String username, boolean isSignedIn, boolean isGuest, boolean isAdmin) {
        this.username = username;
        this.isSignedIn = isSignedIn;
        this.isGuest = isGuest;
        this.isAdmin = isAdmin;
    }

    public ApplicationUser(String username) {
        this(username, false, false, false);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
