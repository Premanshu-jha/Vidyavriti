package org.example.vidyavriti.Controller;

import org.example.vidyavriti.Models.Users;
import org.example.vidyavriti.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    public void studentSignup(@RequestBody Users user){
        usersService.studentSignup(user);
    }

    @PostMapping("/login")
    public void login(@RequestBody Users user){
        usersService.login(user);
    }


}
