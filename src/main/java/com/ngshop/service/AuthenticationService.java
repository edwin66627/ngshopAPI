package com.ngshop.service;

import com.ngshop.dto.security.AuthenticationResponse;
import com.ngshop.dto.security.RefreshTokenRequest;
import com.ngshop.dto.security.SignUpRequest;
import com.ngshop.dto.security.SignInRequest;
import com.ngshop.entity.security.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signin(SignInRequest signinRequest);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
