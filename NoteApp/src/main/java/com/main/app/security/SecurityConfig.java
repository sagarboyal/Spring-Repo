package com.main.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf(csrf ->
                            csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                    .ignoringRequestMatchers("api/auth/public/**")
                    )
                    .authorizeHttpRequests((requests) ->
                    requests
                            .requestMatchers("api/admin/**").hasRole("ADMIN")
                            .requestMatchers(
                                        "/api/notes/**",
                                        "/api/notes",
                                        "api/csrf-token") .permitAll()
                            .anyRequest().authenticated())
                    .httpBasic(withDefaults())
                    .formLogin(withDefaults())
                    .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
