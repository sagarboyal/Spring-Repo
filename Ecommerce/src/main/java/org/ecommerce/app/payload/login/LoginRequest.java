package org.ecommerce.app.payload.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
