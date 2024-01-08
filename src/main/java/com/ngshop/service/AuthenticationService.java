package com.ngshop.service;

import com.ngshop.dto.security.*;
import com.ngshop.entity.security.User;

public interface AuthenticationService {
    UserDTO signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signin(SignInRequest signinRequest);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
