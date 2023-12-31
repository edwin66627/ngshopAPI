package com.ngshop.dto.security;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean emailVerified;
    private Boolean isAdmin;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private Set<AddressDTO> addresses;
    private Set<RoleDTO> roles;

}
