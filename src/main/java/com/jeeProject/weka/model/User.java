package com.jeeProject.weka.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


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

    public void setId(Long id) {
        this.id = id;
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

}
