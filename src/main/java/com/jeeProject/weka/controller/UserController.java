package com.jeeProject.weka.controller;


import com.jeeProject.weka.model.User;
import com.jeeProject.weka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    /**
     * With route localhost:3306/weka/user
     * create a new user with param name and password
     * @param user according to entity user
     * @return new user
     */
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("/users")
    public List getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUsersById(@PathVariable(value = "id") Long userId)
    {
        return userRepository.findById(userId).get();
    }

    @PostMapping("/user")
    public String authentificateUser(User user)
    {
        return "";
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails)
    {
        User user = userRepository.findById(userId).get();
        if(!userDetails.getName().isEmpty() && !userDetails.getPassword().isEmpty())
        {
            user.setName(userDetails.getName());
            user.setPassword(userDetails.getPassword());
        }
        return userRepository.save(user);
    }

    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepository.findById(userId).get();
        if (!user.getName().equals("")) {
            userRepository.delete(user);
            return  true;
        }
        return false;
    }




}