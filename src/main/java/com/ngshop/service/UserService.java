package com.ngshop.service;

import com.ngshop.dto.PaginatedRequestDTO;
import com.ngshop.dto.security.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {
    Page<UserDTO> listUsers(PaginatedRequestDTO request);
    UserDTO getUser(Long userId);
    void updateUser(UserDTO userDTO, Long userId);

    void deleteUser(@PathVariable Long userId);
}
