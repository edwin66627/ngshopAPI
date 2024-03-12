package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.PaginatedRequestDTO;
import com.ngshop.dto.security.UserDTO;
import com.ngshop.entity.Address;
import com.ngshop.entity.security.User;
import com.ngshop.mapper.AddressMapper;
import com.ngshop.mapper.RoleMapper;
import com.ngshop.mapper.UserMapper;
import com.ngshop.repository.UserRepository;
import com.ngshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private AddressMapper addressMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleMapper roleMapper, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public Page<UserDTO> listUsers(PaginatedRequestDTO request) {
        Sort sort = request.getSortDirection().equals(Sort.Direction.ASC.name()) ? Sort.by(request.getSortColumn()).ascending()
                : Sort.by(request.getSortColumn()).descending();
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        return userRepository.findAll(pageable).map(this.userMapper::getUserDto);
    }

    @Override
    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "User", "id", userId))
        );
        return userMapper.getUserDto(user);
    }

    @Override
    public void updateUser(UserDTO userDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "User", userId)));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPhone(userDTO.getPhone());
        user.setEmail(user.getEmail());
        user.setRoles(userDTO.getRoles().stream().map(this.roleMapper::getRole).collect(Collectors.toSet()));

        Set<Address> addresses = userDTO.getAddresses().stream().map(this.addressMapper::getAddress).collect(Collectors.toSet());
        for(Address address: addresses){
            address.setUser(user);
        }

        user.getAddresses().clear();
        user.getAddresses().addAll(addresses);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "User", userId)));

        userRepository.deleteById(userId);
    }


}
