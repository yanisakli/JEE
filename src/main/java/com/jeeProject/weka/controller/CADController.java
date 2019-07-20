package com.jeeProject.weka.controller;

import com.jeeProject.weka.model.CatnDog;
import com.jeeProject.weka.repository.CatnDogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CADController {

    @Autowired
    private CatnDogRepository catnDogRepository;

    @GetMapping("/catndogs")
    public List<CatnDog> getAllOutpout() {

        return catnDogRepository.findAll();
    }

    @PostMapping("/catndog")
    public CatnDog createCatNDog(@Valid @RequestBody CatnDog catnDog)
    {
        return catnDogRepository.save(catnDog);
    }


}
