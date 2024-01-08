package com.ngshop.mapper;

import com.ngshop.dto.AuthorityDTO;
import com.ngshop.entity.security.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorityMapper {
    Authority getAuthority(AuthorityDTO authorityDTO);
    AuthorityDTO getAuthorityDto(Authority authority);
    @Named("WithoutRoles")
    @Mapping(target = "roles", ignore = true)
    AuthorityDTO getAuthorityDtoWithoutRoles(Authority authority);
}
