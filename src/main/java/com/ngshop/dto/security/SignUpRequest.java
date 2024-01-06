package com.ngshop.dto.security;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Set<AddressDTO> addresses;
    private Set<RoleDTO> roles;
}
