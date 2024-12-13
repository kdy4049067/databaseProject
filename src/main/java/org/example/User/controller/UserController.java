package org.example.User.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.User.dto.UserDto;
import org.example.User.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/")
    public String showLoginForm() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDto userDto) {
        String isManager = userService.isManager(userDto.uid());
        boolean isAuthenticated = userService.login(userDto.uid(), userDto.password());

        if(isAuthenticated) {
            if (isManager.equals("manager"))
                return "manager-home";

            return "customer-home";
        }
        return "redirect:/";
    }

}
