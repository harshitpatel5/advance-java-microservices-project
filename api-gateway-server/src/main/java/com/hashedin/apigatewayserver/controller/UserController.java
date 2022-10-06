package com.hashedin.apigatewayserver.controller;


import com.hashedin.apigatewayserver.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    MyUserService userService;

    @GetMapping("/test")
    public String test(){
        return "user okay";
    }

}
