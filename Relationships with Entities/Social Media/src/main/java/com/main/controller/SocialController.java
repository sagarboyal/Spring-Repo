package com.main.controller;

import com.main.entity.SocialUser;
import com.main.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social")
public class SocialController {
    @Autowired
    private SocialService socialService;

    @GetMapping("/users")
    public ResponseEntity<List<SocialUser>> getSocialMediaUsers() {
        return ResponseEntity.status(HttpStatus.FOUND).body(socialService.getAllUsers());
    }
    @PostMapping("/users")
    public ResponseEntity<SocialUser> addSocialMediaUser(@RequestBody SocialUser socialUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socialService.addUser(socialUser));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteSocialMediaUser(@PathVariable("id") long id) {
        socialService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

}
