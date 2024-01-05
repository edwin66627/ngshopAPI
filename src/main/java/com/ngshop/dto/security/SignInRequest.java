package com.ngshop.dto.security;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
