package com.ngshop.controller;

import com.ngshop.dto.PaginatedRequestDTO;
import com.ngshop.dto.security.UserDTO;
import com.ngshop.entity.security.User;
import com.ngshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/list")
    private ResponseEntity<Page<UserDTO>> listUsers(@RequestBody PaginatedRequestDTO paginatedRequestDTO){
        return new ResponseEntity<>(userService.listUsers(paginatedRequestDTO), OK);
    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserDTO> getUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getUser(userId), OK);
    }
}
