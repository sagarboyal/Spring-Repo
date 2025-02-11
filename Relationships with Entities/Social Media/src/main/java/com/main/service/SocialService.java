package com.main.service;

import com.main.entity.SocialUser;
import com.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SocialService {
    @Autowired
    private UserRepository userRepository;

    public List<SocialUser> getAllUsers() {
        return userRepository.findAll();
    }

    public SocialUser addUser(SocialUser socialUser) {
        return userRepository.save(socialUser);
    }

    public SocialUser deleteUser(Long id) {
        SocialUser socialUser = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        userRepository.delete(socialUser);
        return socialUser;
    }
}
