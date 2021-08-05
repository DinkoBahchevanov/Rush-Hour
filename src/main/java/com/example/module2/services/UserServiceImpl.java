package com.example.module2.services;

import com.example.module2.entities.Role;
import com.example.module2.repositories.RoleRepository;
import com.example.module2.web.dtos.UserRequestDto;
import com.example.module2.web.dtos.UserResponseDto;
import com.example.module2.web.dtos.UserMapper;
import com.example.module2.entities.User;
import com.example.module2.repositories.UserRepository;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Set<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        Set<UserResponseDto> userDtos = new HashSet<>();
        for ( User user : users ) {
            userDtos.add(userMapper.mapUserToUserDto(user));
        }
        return userDtos;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto createUserDto) {
        User user = userMapper.mapUserDtoToUser(createUserDto);
        if (userRepository.count() == 0) {
            roleService.seedRolesIntoDB();
            user.setRoles(roleService.getAllRoles());
            user.getRoles().forEach(role -> role.setUsers(Collections.singletonList(user)));
        } else {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                return null;
            }
            Set<Role> roles = new HashSet<>();
            createUserDto.getRoles().forEach(role -> {
               Role role1 =  roleRepository.findByName(role.getName());
               roles.add(role1);
            });
            user.setRoles(roles);
            user.getRoles().forEach(role -> role.getUsers().add(user));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.mapUserToUserDto(userRepository.save(user));
    }

    @Override
    public boolean deleteUserById(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserResponseDto updateUserById(int userId, UserRequestDto userDto) {
        User user = userRepository.getById(userId);
        User uniqueUser = userRepository.findByEmail(userDto.getEmail());

        if (user != null && uniqueUser == null) {
            user = userMapper.mapUserDtoToUser(userDto);
            user.setId(userId);
            return userMapper.mapUserToUserDto(userRepository.save(user));
        }
        return null;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            LOGGER.error("USER NOT FOUND BY EMAIL: " + email);
            throw new UsernameNotFoundException("USER NOT FOUND BY EMAIL: " + email);
        }else {
            LOGGER.info("USER FOUND IN THE DATABASE WITH EMAIL: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public Collection<GrantedAuthority> getAuthorities(User user){
        Collection<GrantedAuthority> authorities = new HashSet<>();
        //Collection<Role> roles = user.getRoles();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
