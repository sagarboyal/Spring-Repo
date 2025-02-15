package com.main.securitydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping
    public String getHome(){
        return "Hello World";
    }
    @PreAuthorize("hasRole('USER')") // for this we need to active method security in config
    @GetMapping("/user")
    public String getHomeUser(){
        return "User Home";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getHomeAdmin(){
        return "Admin Home";
    }
}
