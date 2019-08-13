package com.example.demo.controller;

import com.example.demo.common.httpClient.HttpAPIService;
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
    @RequestMapping(value = "/spring-boot-mybatis")
    public User test(User user){
        userService.insertUser(user);
        return userService.getUserById(user.getUserId());
    }


    @Autowired
    HttpAPIService httpAPIService;
    @RequestMapping(value = "/httpClient")
    public String httpGet() throws Exception {


//        String str = "";
//        System.out.println(str);
//        for (int i = 0; i < 2; i++){
//            try {
//                str = httpAPIService.doGet("http://www.baidu.com");
//            } catch (Throwable e) {
//                System.out.println( "error : "+ e.getMessage());
//            }
//            System.out.println( i +"; "+str);
//        }
        return httpAPIService.doGet("http://www.baidu.com");
    }
}
