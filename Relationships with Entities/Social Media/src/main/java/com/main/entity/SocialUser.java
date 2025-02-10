package com.main.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user") // Managed by UserProfile
    private UserProfile profile;

    @OneToMany(mappedBy = "user")// Managed by Post
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "USER_GROUPS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Groups> groups = new HashSet<>();
}
