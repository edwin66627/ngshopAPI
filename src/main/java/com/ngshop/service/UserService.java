package com.ngshop.service;

import com.ngshop.dto.PaginatedRequestDTO;
import com.ngshop.dto.security.UserDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserDTO> listUsers(PaginatedRequestDTO request);
    UserDTO getUser(Long userId);
}
