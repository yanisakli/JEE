package com.jeeProject.weka.controller;

import com.jeeProject.weka.exception.NotFoundException;
import com.jeeProject.weka.model.Modele;
import com.jeeProject.weka.repository.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChartController {


    @Autowired
    private ModeleRepository modeleRepository;


    @GetMapping("/displayPieChart/{id}")
    public String pieChart(Model model, @PathVariable(value = "id") Long modeleID) {
        Modele modele = modeleRepository.findById(modeleID).get();
        if (modele.getName().isEmpty()) {
            throw new NotFoundException("Model haven't been found");
        }
        model.addAttribute("correct_instance", modele.getCorrect_instances());
        model.addAttribute("incorrect_instance", modele.getIncorrect_instances());
        return "pieChart";
    }


    @GetMapping("/displayBarGraph")
    public String barGraph(Model model) {
        Map<String, Double> kappaMap = new LinkedHashMap<>();
        List<Modele> modeles = modeleRepository.findAll();
        if (modeles.isEmpty()) {
            throw new NotFoundException("Model haven't been found");
        }
        for (Modele modele : modeles) {
            if (modele.getKappa_stat() != 0) {
                kappaMap.put(modele.getName(), modele.getKappa_stat());
            }
        }
        model.addAttribute("kappaMap", kappaMap);
        return "barGraph";
    }

}
