package com.main.app.security;

import com.main.app.jwt.AuthEntryPoint;
import com.main.app.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final AuthEntryPoint unauthorizedHandler;

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
                                        "api/csrf-token",
                                        "api/auth/public/**",
                                        "/oauth2/**") .permitAll()
                            .anyRequest().authenticated())
                    .oauth2Login(oauth -> {
                    })
                    .exceptionHandling(exception ->
                                    exception.authenticationEntryPoint(unauthorizedHandler))
                    .addFilterBefore(authTokenFilter(),
                            UsernamePasswordAuthenticationFilter.class)
                    .httpBasic(withDefaults())
                    .formLogin(withDefaults())
                    .build();
    }

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }
}
