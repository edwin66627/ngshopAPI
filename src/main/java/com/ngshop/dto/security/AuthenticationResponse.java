package com.ngshop.dto.security;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
}
