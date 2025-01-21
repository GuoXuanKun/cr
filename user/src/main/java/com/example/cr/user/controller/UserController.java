package com.example.cr.user.controller;

import com.example.cr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/count")
    public long count() {
        return userService.count();
    }

    @PostMapping("/register")
    public int register(String mobile) {
        return userService.register(mobile);
    }



}
