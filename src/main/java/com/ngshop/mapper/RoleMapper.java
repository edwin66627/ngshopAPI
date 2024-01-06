package com.ngshop.mapper;

import com.ngshop.dto.security.RoleDTO;
import com.ngshop.entity.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleDTO getRoleDto(Role role);
    Role getRole(RoleDTO roleDTO);
}
