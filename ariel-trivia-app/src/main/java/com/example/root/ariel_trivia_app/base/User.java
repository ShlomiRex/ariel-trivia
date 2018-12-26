package com.example.root.ariel_trivia_app.base;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

public class User implements Mappable {
    @Id
    private String id;
    private boolean isAdmin;
    private LoginInfo loginInfo;

    public User(LoginInfo loginInfo, boolean isAdmin) {
        this.loginInfo = loginInfo;
        this.isAdmin = isAdmin;
        this.id = ""+System.currentTimeMillis();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public Document write(NitriteMapper mapper) {
        Document document = new Document();
        document.put("id", id);
        document.put("username", getLoginInfo().getUsername());
        document.put("password", getLoginInfo().getPassword_sha256());
        document.put("isAdmin", isAdmin);
        return document;
    }

    @Override
    public void read(NitriteMapper mapper, Document document) {
        this.id = document.get("id").toString();
        String username = document.get("username").toString();
        String password = document.get("password").toString();
        this.loginInfo = new LoginInfo(username, password);
        this.isAdmin = (boolean) document.get("isAdmin");
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
