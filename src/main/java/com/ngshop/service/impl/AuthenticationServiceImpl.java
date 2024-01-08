package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.security.*;
import com.ngshop.entity.Address;
import com.ngshop.entity.security.User;
import com.ngshop.mapper.UserMapper;
import com.ngshop.repository.UserRepository;
import com.ngshop.security.JWTTokenProvider;
import com.ngshop.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JWTTokenProvider tokenProvider, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO signUp(SignUpRequest signUpRequest){
        User user = this.userMapper.getUser(signUpRequest);
        for(Address address: user.getAddresses()){
            address.setUser(user);
        }

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User userSaved = userRepository.save(user);
        return this.userMapper.getUserDto(userSaved);
    }

    @Override
    public AuthenticationResponse signin(SignInRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),
                signinRequest.getPassword()));
        User user = userRepository.findByUsername(signinRequest.getUsername()).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.INVALID_CREDENTIALS));

        var jwt = tokenProvider.generateToken(user);
        var refreshToken = tokenProvider.generateRefreshToken(new HashMap<>(), user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        authenticationResponse.setRefreshToken(refreshToken);
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = tokenProvider.extractUsername(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "User",username)));

        if(tokenProvider.isTokenValid(refreshTokenRequest.getRefreshToken(), user)){
            var jwt = tokenProvider.generateToken(user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(jwt);
            authenticationResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());
            return authenticationResponse;
        }
        return null;
    }

}
