package com.jeeProject.weka.controller;

import com.jeeProject.weka.model.Chart;
import com.jeeProject.weka.repository.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ChartController {


    @Autowired
    private ChartRepository chartRepository;


    @PostMapping("/graph")
    public Chart createGraph(@Valid @RequestBody Chart chart)
    {
        return chartRepository.save(chart);
    }




}
