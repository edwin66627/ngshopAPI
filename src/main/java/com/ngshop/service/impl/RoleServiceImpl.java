package com.ngshop.service.impl;

import com.ngshop.dto.security.RoleDTO;
import com.ngshop.mapper.RoleMapper;
import com.ngshop.repository.RoleRepository;
import com.ngshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listRoles() {
        return roleRepository.findAll().stream().map(this.roleMapper::getRoleDtoWithoutRelatedData).collect(Collectors.toList());
    }
}
