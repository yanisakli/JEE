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

    public User() {
        super();
    }

    public User(Long id, String name) {
        super();
        this.id = id;
        this.name = name;

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

}
