package org.ecommerce.app.util;

import org.ecommerce.app.model.User;
import org.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    @Autowired
    UserRepository userRepository;

    public String loggedInEmail() {
        return loggedInUser().getEmail();
    }

    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
