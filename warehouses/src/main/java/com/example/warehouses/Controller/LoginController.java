package com.example.warehouses.Controller;

import com.example.warehouses.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/myapp/login")
public class LoginController {

    MyUserDetailsService myUserDetailsService;
    @Autowired
    LoginController(MyUserDetailsService myUserDetailsService) {this.myUserDetailsService = myUserDetailsService;}

    @GetMapping
    public UserDetails login(){

        return myUserDetailsService.loadUserByUsername("client1@gmail.com");
    }


}
