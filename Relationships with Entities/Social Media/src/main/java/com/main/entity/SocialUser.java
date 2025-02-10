package com.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // Managed by UserProfile
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


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setProfile(UserProfile profile) {
        profile.setUser(this);
        this.profile = profile;
    }
}
