package com.jeeProject.weka.controller;


import com.jeeProject.weka.model.User;
import com.jeeProject.weka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    // Get All Notes
    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}