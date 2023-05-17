package com.example.spring.controller;

import com.example.spring.domain.entity.UserInformation;
import com.example.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/register")
  public UserInformation register(@RequestBody UserInformation userInformation) {
    return userService.register(userInformation);
  }
}
