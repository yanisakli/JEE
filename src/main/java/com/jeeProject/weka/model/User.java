package com.jeeProject.weka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    private String token;

    private Timestamp token_expiration;


    public User() {
        super();
    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;


    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Timestamp getToken_expiration() { return token_expiration; }

    public void setToken_expiration(Timestamp token_expiration) { this.token_expiration = token_expiration; }
}
