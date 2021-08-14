package com.example.module2.services;

import com.example.module2.entities.Role;

import java.util.Set;

public interface RoleService {

    void seedRolesIntoDB();

    Set<Role> getAllRoles();

    Set<Role> getRolesByUserEmail(String email);

    Role getRoleByName(String name);
}
