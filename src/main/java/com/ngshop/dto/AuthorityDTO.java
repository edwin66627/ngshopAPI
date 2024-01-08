package com.ngshop.dto;

import com.ngshop.dto.security.RoleDTO;
import lombok.Data;

import java.util.Set;

@Data
public class AuthorityDTO {
    private Integer id;
    private String permission;
    private Set<RoleDTO> roles;
}
