package com.ngshop.controller;

import com.ngshop.dto.security.AuthenticationResponse;
import com.ngshop.dto.security.RefreshTokenRequest;
import com.ngshop.dto.security.SignUpRequest;
import com.ngshop.dto.security.SignInRequest;
import com.ngshop.entity.security.User;
import com.ngshop.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(authenticationService.signUp(signUpRequest), CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(authenticationService.signin(signInRequest), OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authenticationService.refreshToken(refreshTokenRequest), OK);
    }

}
