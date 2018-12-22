package com.example.root.ariel_trivia_app.base;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class LoginInfo implements Mappable {
    private String username, password_sha256;
    public LoginInfo(String username, String password_sha256) {
        this.username = username;
        this.password_sha256 = password_sha256.toUpperCase();
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
        this.password_sha256 = password_sha256.toUpperCase();
    }

    public Document toDocument() {
        Document d = new Document();
        d.put("username", username);
        d.put("password", password_sha256);
        return d;
    }

    @Override
    public Document write(NitriteMapper mapper) {
        Document document = new Document();
        document.put("username", username);
        document.put("password", password_sha256);
        return document;
    }

    @Override
    public void read(NitriteMapper mapper, Document document) {
        username = (String) document.get("username");
        password_sha256 = (String) document.get("password");

    }
}
