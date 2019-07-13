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
    private double correct_instances;


    private double incorrect_instances;
    private int nb_instance;
    private double kappa_stat;


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

    public double getCorrect_instances() { return correct_instances; }

    public void setCorrect_instances(double correct_instances) { this.correct_instances = correct_instances; }

    public double getIncorrect_instances() { return incorrect_instances; }

    public void setIncorrect_instances(double incorrect_instances) { this.incorrect_instances = incorrect_instances; }

    public int getNb_instance() { return nb_instance; }

    public void setNb_instance(int nb_instance) { this.nb_instance = nb_instance; }

    public double getKappa_stat() { return kappa_stat; }

    public void setKappa_stat(double kappa_stat) { this.kappa_stat = kappa_stat; }


}
