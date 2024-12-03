package org.example.controller;

import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam String uid, @RequestParam String password){
        boolean isAuthenticated = userService.login(uid, password);
        if(isAuthenticated)
            return "/login/" + uid;
        return "로그인 error 발생";
    }

}
