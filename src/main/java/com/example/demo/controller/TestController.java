package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
//localhost:8000/api/test

    @Autowired
    UserService userService;
    @RequestMapping(value = "/test")
    public User test(User user){
        userService.insertUser(user);
        return userService.getUserById(user.getUserId());
    }
}
