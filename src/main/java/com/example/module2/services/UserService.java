package com.example.module2.services;

import com.example.module2.entities.Role;
import com.example.module2.entities.User;
import com.example.module2.web.dtos.UserRequestDto;
import com.example.module2.web.dtos.UserResponseDto;

import javax.transaction.Transactional;
import java.util.Set;

public interface UserService {

    Set<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto createUserDto);

    boolean deleteUserById(int id);

    UserResponseDto updateUserById(int id, UserRequestDto userDto);

    void addRoleToUser(String email, String roleName);

    User getUserByEmail(String email);
}
