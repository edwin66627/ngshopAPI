package com.ngshop.mapper;

import com.ngshop.dto.security.RoleDTO;
import com.ngshop.entity.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(uses = AuthorityMapper.class, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    Role getRole(RoleDTO roleDTO);
    @Mapping(target = "authorities", qualifiedByName = "WithoutRoles")
    RoleDTO getRoleDto(Role role);
}
