package com.ngshop.service;

import com.ngshop.dto.PaginatedRequestDTO;
import com.ngshop.dto.security.UserDTO;
import com.ngshop.entity.security.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<UserDTO> listUsers(PaginatedRequestDTO request);
}
