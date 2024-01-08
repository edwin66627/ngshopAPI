package com.ngshop.dto.security;

import com.ngshop.entity.security.Authority;
import lombok.*;

import java.util.Set;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private Set<Authority> authorities;

}
