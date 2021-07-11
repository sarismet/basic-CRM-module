package com.layermark.layermark_sarismet.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class JwtRequest {
    private String username;
    private String password;
    private String email;

    public void setUsername(String username) {
        this.username = username;
    }

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
}
