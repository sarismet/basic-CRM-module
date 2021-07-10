package com.layermark.layermark_sarismet.model;

public class User {

    private String id;
    private String username ;
    private String password ;
    private String email ;
    private boolean isAdmin ;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {
    }
}
