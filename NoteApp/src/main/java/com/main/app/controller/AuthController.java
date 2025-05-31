package com.main.app.controller;

import com.main.app.model.Role;
import com.main.app.model.Roles;
import com.main.app.model.User;
import com.main.app.payload.request.LoginRequest;
import com.main.app.payload.request.SignupRequest;
import com.main.app.payload.response.LoginResponse;
import com.main.app.jwt.JwtUtils;
import com.main.app.payload.response.MessageResponse;
import com.main.app.payload.response.UserInfoResponse;
import com.main.app.repository.RoleRepository;
import com.main.app.repository.UserRepository;
import com.main.app.security.services.UserDetailsImpl;
import com.main.app.service.TotpService;
import com.main.app.service.UserService;
import com.main.app.utils.AuthUtils;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final AuthUtils authUtils;
    private final TotpService totpService;

    @PostMapping("/public/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        return ResponseEntity.ok(LoginResponse.builder()
                .username(userDetails.getUsername())
                .roles(roles)
                .jwtToken(jwtToken)
                .build());
    }

    @PostMapping("/public/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Role role;

        if (strRoles == null || strRoles.isEmpty()) {
            role = roleRepository.findByRoleName(Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        } else {
            String roleStr = strRoles.iterator().next();
            if (roleStr.equals("admin")) {
                role = roleRepository.findByRoleName(Roles.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            } else {
                role = roleRepository.findByRoleName(Roles.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }

            user.setAccountNonLocked(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
            user.setAccountExpiryDate(LocalDate.now().plusYears(1));
            user.setTwoFactorEnabled(false);
            user.setSignUpMethod("email");
        }
        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.isTwoFactorEnabled(),
                roles
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUsername(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(user.getUsername() != null ? user.getUsername() : "");
    }

    @PostMapping("/public/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam String email) {
        try {
            userService.generatePasswordResetToken(email);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid email!"));
        }
        return ResponseEntity.ok(new MessageResponse("Password reset token generated successfully!"));
    }

    @PostMapping("/public/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token,
                                           @RequestParam String newPassword) {
        try {
            userService.resetPassword(token, newPassword);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid token!"));
        }
        return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
    }

    @PostMapping("/enable-2fa")
    public ResponseEntity<?> enable2FA() {
        Long userId = authUtils.loggedInUserId();
        GoogleAuthenticatorKey key = userService.generateAuthenticatorKey(userId);
        String qrCodeUrl = totpService.getQrCodeUrl(key, authUtils.loggedInUserName());
        return ResponseEntity.ok(qrCodeUrl);
    }

    @PostMapping("/disable-2fa")
    public ResponseEntity<?> disable2FA() {
        Long userId = authUtils.loggedInUserId();
        userService.disable2FA(userId);
        return ResponseEntity.ok("Disabled 2FA");
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify2FA(@RequestParam int code) {
        Long userId = authUtils.loggedInUserId();
        boolean isValid = userService.validate2FACode(userId, code);
        if (isValid) userService.enable2FA(userId);
        return isValid ? ResponseEntity.ok("Your 2FA code verified successfully!") :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Invalid 2FA code!"));
    }

    @GetMapping("/user/2fa-status")
    public ResponseEntity<?> status() {
        User user = authUtils.loggedInUser();
        return ResponseEntity.ok(
                Map.of("is2faEnabled", user.isTwoFactorEnabled())
        );
    }

    @PostMapping("/public/verify-2fa-login")
    public ResponseEntity<?> verify2FALogin(
            @RequestParam int code,
            @RequestParam String jwtToken) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
        User user = userService.findByUsername(username);
        boolean isValid = userService.validate2FACode(user.getUserId(), code);
        return isValid ? ResponseEntity.ok("Your 2FA code verified successfully!") :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Invalid 2FA code!"));

    }

}
