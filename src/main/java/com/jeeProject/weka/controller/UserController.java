package com.jeeProject.weka.controller;


import com.jeeProject.weka.exception.UserBadRequestException;
import com.jeeProject.weka.exception.UserNotFoundException;
import com.jeeProject.weka.exception.UserUnauthorizedExecption;
import com.jeeProject.weka.model.User;
import com.jeeProject.weka.repository.UserRepository;
import com.jeeProject.weka.service.TokenHandlerService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private TokenHandlerService randomToken = new TokenHandlerService();


    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        List<User> listUser = userRepository.findAll();
        if (user.getName().isEmpty() || user.getPassword().isEmpty()) {
            throw new UserBadRequestException("One of both champ is missing");
        }
        if (!listUser.isEmpty()) {
            for (User oneUser : listUser) {
                if (user.getName().equals(oneUser.getName())) {
                    throw new UserBadRequestException("User already exist");
                }
            }
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        randomToken.setToken();
        user.setToken(randomToken.getToken());
        user.setToken_expiration(new Timestamp(System.currentTimeMillis() + 36000000));
        return userRepository.save(user);
    }

    @PostMapping("/auth")
    public User authentificate(@Valid @RequestBody User user) {
        List<User> listUser = userRepository.findAll();
        User auth = new User();
        if (!listUser.isEmpty()) {
            for (User oneuser : listUser) {
                if (oneuser.getName().equals(user.getName()) && BCrypt.checkpw(user.getPassword(), oneuser.getPassword())) {
                    auth = oneuser;
                    break;
                }
            }
        } else {
            throw new UserNotFoundException("User haven't been found");
        }
        if (auth.getName().isEmpty()) {
            throw new UserNotFoundException("User haven't been found");
        }
        user.setToken_expiration(new Timestamp(System.currentTimeMillis() + 36000000));
        userRepository.save(auth);
        return auth;
    }


    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId).get();
    }


    @GetMapping("/userToken")
    public User getUserWithToken(@RequestHeader("x-access-token") String token) {
        List<User> list = userRepository.findAll();
        User user = new User();
        if (token.isEmpty()) {
            throw new UserBadRequestException("Token is missing !");
        }
        if (!list.isEmpty()) {
            for (User oneuser : list) {
                if (oneuser.getToken().equals(token)) {
                    user = oneuser;
                }
            }
        } else {
            throw new UserNotFoundException("User haven't been found");
        }
        if (user.getName().isEmpty()) {
            throw new UserNotFoundException("User haven't been found");
        }
        return user;
    }

    @PutMapping("/user/update")
    public User updateUser(@RequestHeader("x-access-token") String token, @Valid @RequestBody User newValue) {
        User user = getUserWithToken(token);
        Timestamp currentTImestamp = new Timestamp(System.currentTimeMillis());
        if (currentTImestamp.after(user.getToken_expiration())) {
            throw new UserUnauthorizedExecption("Token invalid, you have to re-authentified yourself !");
        }
        user.setName(newValue.getName());
        user.setPassword(BCrypt.hashpw(newValue.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }

    @DeleteMapping("/user/delete")
    public boolean deleteUser(@RequestHeader("x-access-token") String token) {
        User user = getUserWithToken(token);
        Timestamp currentTImestamp = new Timestamp(System.currentTimeMillis());
        if (currentTImestamp.after(user.getToken_expiration())) {
            throw new UserUnauthorizedExecption("Token invalid, you have to re-authentified yourself !");
        }
        userRepository.delete(user);
        return true;
    }
}