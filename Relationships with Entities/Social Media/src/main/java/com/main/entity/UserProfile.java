package com.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToOne(mappedBy = "profile") // using mappedBy annotation the ownership go to the User entity.
    // (it search for profile field in user class if it was found it was mapped and its don't create any foreign key column here.)
    //@JoinColumn(name = "social_user") // explicitly add foreign key column by jpa
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private SocialUser user;

}
