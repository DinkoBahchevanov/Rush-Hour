package com.example.module2.services;

import com.example.module2.entities.Role;
import com.example.module2.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesIntoDB() {
        this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
        this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Set<Role> getRolesByUserEmail(String email) {
        return roleRepository.findByUserId(email);
    }
}
