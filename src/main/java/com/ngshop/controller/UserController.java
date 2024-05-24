package com.ngshop.controller;

import com.ngshop.constant.ResponseMessage;
import com.ngshop.dto.PaginatedRequestDTO;
import com.ngshop.dto.security.UserDTO;
import com.ngshop.entity.HttpResponse;
import com.ngshop.entity.security.User;
import com.ngshop.security.permissions.UserDeletePermission;
import com.ngshop.security.permissions.UserReadPermission;
import com.ngshop.security.permissions.UserUpdatePermission;
import com.ngshop.service.UserService;
import com.ngshop.utils.ResponseUtility;
import jakarta.validation.Valid;
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

    @UserReadPermission
    @PostMapping("/list")
    public ResponseEntity<Page<UserDTO>> listUsers(@RequestBody PaginatedRequestDTO paginatedRequestDTO){
        return new ResponseEntity<>(userService.listUsers(paginatedRequestDTO), OK);
    }

    @UserReadPermission
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getUser(userId), OK);
    }

    @UserUpdatePermission
    @PutMapping("/{userId}")
    public ResponseEntity<HttpResponse> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long userId){
        userService.updateUser(userDTO, userId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.UPDATE_SUCCESS, "User"), OK);
    }

    @UserDeletePermission
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.DELETE_SUCCESS, "User"), OK);
    }
}
