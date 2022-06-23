package com.coders.tournament.controllers;

import com.coders.tournament.model.UserRequest;
import com.coders.tournament.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {



    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public String signUp(@RequestBody UserRequest userRequest) {
        return userService.signUp(userRequest.getLogin(), userRequest.getPassword());
    }

    @PostMapping("/auth")
    public Long signIn(@RequestBody UserRequest userRequest) {
        return userService.signIn(userRequest.getLogin(), userRequest.getPassword());
    }
}
