package com.jeeProject.weka.controller;

import com.jeeProject.weka.exception.NotFoundException;
import com.jeeProject.weka.model.Chart;
import com.jeeProject.weka.repository.ChartRepository;
import com.jeeProject.weka.repository.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ChartController {


    @Autowired
    private ChartRepository chartRepository;
    @Autowired
    private ModeleRepository modeleRepository;


    @PostMapping("/chart")
    public Chart createChart(@Valid @RequestBody Chart chart)
    {
        return chartRepository.save(chart);
    }

    @PutMapping("/chart/{id}")
    public Chart uploadFile(@PathVariable(value = "id") Long chartID)
    {
        List<Chart> charts = chartRepository.findAll();
        Chart chart = new Chart();
        for (Chart chart1: charts) {
            if(chartID.equals(chart1.getId())) {
                chart = chart1;
            }
        }
        if(chart.getName().isEmpty())
        {
            throw new NotFoundException("Chart hasn't been found");
        }

        return  chart;

    }




}
