package com.main.entity;

import jakarta.persistence.*;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profile") // using mappedBy annotation the ownership go to the User entity.
    // (it search for profile field in user class if it was found it was mapped and its don't create any foreign key column here.)
    //@JoinColumn(name = "social_user") // explicitly add foreign key column by jpa
    private SocialUser user;
}
