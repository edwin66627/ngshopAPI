package com.ngshop.mapper;

import com.ngshop.dto.security.SignUpRequest;
import com.ngshop.dto.security.UserDTO;
import com.ngshop.entity.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(uses = RoleMapper.class, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User getUserToSignUp(SignUpRequest signUpRequest);
    User getUser(UserDTO userDTO);
    UserDTO getUserDto(User user);
    @Named("WithoutRoles")
    @Mapping(target = "roles", ignore = true)
    User getUserWithoutRoles(UserDTO userDTO);
    @Named("WithoutRelatedData")
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDTO getUserDtoWithoutRelatedData(User user);
}

