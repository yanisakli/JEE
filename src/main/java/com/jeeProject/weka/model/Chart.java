package com.jeeProject.weka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "graph")
public class Chart {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String file;

    public Chart() {
        super();
    }

    public Chart(String name) {
        super();
        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() { return file; }

    public void setFile(String file) { this.file = file; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
