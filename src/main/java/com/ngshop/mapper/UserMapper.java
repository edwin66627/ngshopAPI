package com.ngshop.mapper;

import com.ngshop.dto.security.SignUpRequest;
import com.ngshop.entity.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User getUser(SignUpRequest signUpRequest);
}
