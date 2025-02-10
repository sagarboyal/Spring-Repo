package com.main.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user")
    private UserProfile profile;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
}
