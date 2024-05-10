package com.ngshop.controller;

import com.ngshop.dto.security.RoleDTO;
import com.ngshop.security.permissions.RoleReadPermission;
import com.ngshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.OK;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RoleReadPermission
    @GetMapping("/list")
    private ResponseEntity<List<RoleDTO>> listRoles(){
        return new ResponseEntity<>(roleService.listRoles(), OK);
    }
}
