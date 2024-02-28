package com.ngshop.service.impl;

import com.ngshop.dto.PaginatedRequestDTO;
import com.ngshop.dto.security.UserDTO;
import com.ngshop.entity.security.User;
import com.ngshop.mapper.UserMapper;
import com.ngshop.repository.UserRepository;
import com.ngshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDTO> listUsers(PaginatedRequestDTO request) {
        Sort sort = request.getSortDirection().equals(Sort.Direction.ASC.name()) ? Sort.by(request.getSortColumn()).ascending()
                : Sort.by(request.getSortColumn()).descending();
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        return userRepository.findAll(pageable).map(this.userMapper::getUserDto);

    }
}
