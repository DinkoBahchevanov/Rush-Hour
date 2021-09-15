package com.example.module2.services;

import com.example.module2.entities.Role;
import com.example.module2.exceptions.users.AccessDeniedException;
import com.example.module2.exceptions.users.EmailAlreadyExistsException;
import com.example.module2.exceptions.users.UserNotFoundException;
import com.example.module2.web.dtos.userDtos.UserRequestDto;
import com.example.module2.web.dtos.userDtos.UserResponseDto;
import com.example.module2.web.dtos.userDtos.UserMapper;
import com.example.module2.entities.User;
import com.example.module2.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Set<UserResponseDto> getAllUsers() {
        List<UserResponseDto> userDtos = userMapper.mapUserListToUserResponseDtoList(userRepository.findAll());
        return new HashSet<>(userDtos);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto createUserDto) {
        User user = userMapper.mapUserDtoToUser(createUserDto);
        user.setRoles(new HashSet<>());
        for ( int i = 0; i < createUserDto.getRoles().size(); i++ ) {
            Role role = roleService.getRoleByName(createUserDto.getRoles().get(0).getName());
            user.getRoles().add(role);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUserById(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        LOGGER.error(String.format("User with ID: %d - not found", id));
        throw new UserNotFoundException(id);
    }

    @Override
    public UserResponseDto updateUserById(int userId, String currentUserEmail, UserRequestDto userDto) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        User uniqueUser = userRepository.findByEmail(userDto.getEmail());
        User changer = userRepository.findByEmail(currentUserEmail);

        if (!user.isPresent()) {
            LOGGER.error(String.format("User with ID: %d - not found", userId));
            throw new UserNotFoundException(userId);
        }

        if (uniqueUser != null) {
            LOGGER.error(String.format("Email %s already exists", userDto.getEmail()));
            throw new EmailAlreadyExistsException(userDto.getEmail());
        }

        if (changer.getRoles().stream().noneMatch(role -> role.getName().contains("ROLE_ADMIN"))
                && user.get().getRoles().stream().anyMatch(role -> role.getName().contains("ROLE_ADMIN"))) {
            LOGGER.error(String.format("Not enough rights for the user with id: %d", changer.getId()));
            throw new AccessDeniedException();
        }

        if (changer.getRoles().stream().noneMatch(role -> role.getName().contains("ROLE_ADMIN"))
                && user.get().getRoles().stream().noneMatch(role -> role.getName().contains("ROLE_ADMIN")) && changer.getId() != userId) {
            LOGGER.error(String.format("Not enough rights for the user with id: %d", changer.getId()));
            throw new AccessDeniedException();
        }
        user = Optional.ofNullable(userMapper.mapUserDtoToUser(userDto));
        user.get().setId(userId);
        user.get().setPassword(passwordEncoder.encode(user.get().getPassword()));
        return userMapper.mapUserToUserDto(userRepository.save(user.get()));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto createUserDto) {
        User user = userMapper.mapUserDtoToUser(createUserDto);
        user.setRoles(Set.of(roleService.getRoleByName("ROLE_USER")));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public void seedAdminToDB() {
        User user = new User();
        user.setFirstName("Dinko");
        user.setLastName("Bahchevanov");
        user.setEmail("dinko@abv.bg");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRoles(roleService.getAllRoles());
        userRepository.save(user);
    }
}
