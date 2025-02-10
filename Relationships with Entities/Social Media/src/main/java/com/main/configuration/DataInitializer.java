package com.main.configuration;

import com.main.entity.Groups;
import com.main.entity.Post;
import com.main.entity.SocialUser;
import com.main.entity.UserProfile;
import com.main.repository.GroupRepository;
import com.main.repository.PostRepository;
import com.main.repository.ProfileRepository;
import com.main.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;

    public DataInitializer(UserRepository userRepository, ProfileRepository profileRepository, PostRepository postRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return( args -> {
            SocialUser socialUser1 = new SocialUser();
            SocialUser socialUser2 = new SocialUser();
            SocialUser socialUser3 = new SocialUser();

            userRepository.save(socialUser1);
            userRepository.save(socialUser2);
            userRepository.save(socialUser3);

            Groups group1 = new Groups();
            Groups group2 = new Groups();

            group1.getUsers().add(socialUser1);
            group1.getUsers().add(socialUser2);

            group2.getUsers().add(socialUser2);
            group2.getUsers().add(socialUser3);

            groupRepository.save(group1);
            groupRepository.save(group2);

            socialUser1.getGroups().add(group1);
            socialUser2.getGroups().add(group1);
            socialUser2.getGroups().add(group2);
            socialUser3.getGroups().add(group2);

            userRepository.save(socialUser1);
            userRepository.save(socialUser2);


            Post post1 = Post.builder()
                    .user(socialUser1)
                    .build();
            Post post2 = Post.builder()
                    .user(socialUser2)
                    .build();
            Post post3 = Post.builder()
                    .user(socialUser3)
                    .build();

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            UserProfile userProfile1 = UserProfile.builder()
                    .user(socialUser1)
                    .build();
            UserProfile userProfile2 = UserProfile.builder()
                    .user(socialUser2)
                    .build();
            UserProfile userProfile3 = UserProfile.builder()
                    .user(socialUser3)
                    .build();

            profileRepository.save(userProfile1);
            profileRepository.save(userProfile2);
            profileRepository.save(userProfile3);
        });
    }
}
