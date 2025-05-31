package com.main.app.serviceImpl;

import com.main.app.service.TotpService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TotpServiceImpl implements TotpService {

    private final GoogleAuthenticator gAuth;

    @Override
    public GoogleAuthenticatorKey generateSecret() {
        return gAuth.createCredentials();
    }

    @Override
    public String getQrCodeUrl(GoogleAuthenticatorKey key, String username) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("Secure Notes Application", username, key);
    }

    @Override
    public boolean verifyKey(String secret, int code) {
        return gAuth.authorize(secret, code);
    }
}
