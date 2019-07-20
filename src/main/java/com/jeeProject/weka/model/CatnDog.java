package com.jeeProject.weka.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "catndog")
public class CatnDog {


    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String description;
    private String output;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public CatnDog() {
        super();
    }

    public CatnDog(String description) {
        super();
        this.description = description;
    }


}
