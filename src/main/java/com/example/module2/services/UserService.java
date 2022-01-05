package com.example.module2.services;
import com.example.module2.entities.User;
import com.example.module2.exceptions.userExc.UserNotFoundByIdException;
import com.example.module2.web.dtos.userDtos.UserRequestDto;
import com.example.module2.web.dtos.userDtos.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface UserService {

    Set<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto createUserDto);

    ResponseEntity<HttpStatus> deleteUserById(int id);

    UserResponseDto updateUserById(int userId, String email, UserRequestDto userDto) throws UserNotFoundByIdException;

    User getUserByEmail(String email);

    UserResponseDto registerUser(UserRequestDto createUserDto);

    void seedAdminToDB();
}
