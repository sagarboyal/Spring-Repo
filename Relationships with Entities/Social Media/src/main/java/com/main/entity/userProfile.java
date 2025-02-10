package com.main.entity;

import jakarta.persistence.*;

@Entity
public class userProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "social_user") // explicitly add foreign key column by jpa
    private SocialUser user;
}
