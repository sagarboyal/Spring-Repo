package com.main.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "groups")
    private Set<SocialUser> users = new HashSet<SocialUser>();
}
