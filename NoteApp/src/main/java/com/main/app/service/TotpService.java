package com.main.app.service;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public interface TotpService {
    GoogleAuthenticatorKey generateSecret();
    String getQrCodeUrl(GoogleAuthenticatorKey key, String username);
    boolean verifyKey(String secret, int code);
}
