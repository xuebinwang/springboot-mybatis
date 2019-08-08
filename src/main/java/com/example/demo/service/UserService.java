package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    void insertUser(User user);
    User getUserById(int userId);
    List<User> getAll();
}
