package com.jeeProject.weka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "modele")
public class Modele {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    private String file;
    @NotNull
    private long idUser;
    private String reponse;

    public Modele() {
        super();
    }

    public Modele(String name, long idUser) {
        super();
        this.name = name;
        this.idUser = idUser;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getReponse() { return reponse; }

    public void setReponse(String reponse) { this.reponse = reponse; }
}
