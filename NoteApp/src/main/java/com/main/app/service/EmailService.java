package com.main.app.service;

public interface EmailService {
    void sendPasswordResetEmail(String email, String resetUrl);
}
