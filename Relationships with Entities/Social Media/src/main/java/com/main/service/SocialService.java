package com.main.service;

import com.main.entity.SocialUser;
import com.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
