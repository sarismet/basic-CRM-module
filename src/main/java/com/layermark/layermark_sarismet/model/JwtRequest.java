package com.layermark.layermark_sarismet.model;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
    private String email;

    public JwtRequest(String username,String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public JwtRequest() {

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
