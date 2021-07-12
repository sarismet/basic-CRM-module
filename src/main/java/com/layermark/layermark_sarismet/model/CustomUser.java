package com.layermark.layermark_sarismet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "customusers")
public class CustomUser {

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid2", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "user_id")
    private String userID;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String role;

    public String getUserID() {
        return userID;
    }

    @Column
    private String email;
    @Column
    private Boolean isVerified;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}