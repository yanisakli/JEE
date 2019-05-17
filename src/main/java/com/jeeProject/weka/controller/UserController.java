package com.jeeProject.weka.controller;


import com.jeeProject.weka.model.User;
import com.jeeProject.weka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * With route localhost:3306/weka/user
     * create a new user with param name and password
     * @param user according to entity user
     * @return new user
     */
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

}