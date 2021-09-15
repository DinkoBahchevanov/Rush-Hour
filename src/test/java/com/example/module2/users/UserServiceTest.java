package com.example.module2.users;

import com.example.module2.entities.Role;
import com.example.module2.entities.User;
import com.example.module2.exceptions.users.UserNotFoundException;
import com.example.module2.repositories.UserRepository;
import com.example.module2.services.RoleService;
import com.example.module2.services.UserService;
import com.example.module2.services.UserServiceImpl;
import com.example.module2.web.dtos.userDtos.UserMapper;
import com.example.module2.web.dtos.userDtos.UserMapperImpl;
import com.example.module2.web.dtos.userDtos.UserRequestDto;
import com.example.module2.web.dtos.userDtos.UserResponseDto;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private UserService underTest;
    private User user;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
        userRepository = mock(UserRepository.class);
        roleService = mock(RoleService.class);
        passwordEncoder = new BCryptPasswordEncoder();
        underTest = new UserServiceImpl(userRepository, userMapper,
                roleService, passwordEncoder);

        user = new User("Dinko", "Bahchevanov", "dinko@abv.bg", "1234");
    }

    //get users
    @Test
    void getAllUsersShouldBeEmpty() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        Set<UserResponseDto> userResponseDtos = this.underTest.getAllUsers();
        assertThat(userResponseDtos.size()).isEqualTo(0);
    }

    //get users
    @Test
    void getAllUsersShouldReturnAllUsers() {
        List<User> list = new ArrayList<>();
        User userOne = new User("dinko", "Bahchevanov", "dinko@abv.bg", "1234");
        User userTwo = new User("boja", "Petrova", "boja@abv.bg", "1234");
        User userThree = new User("mama", "Bahchevanova", "mama@abv.bg", "1234");

        list.add(userOne);
        list.add(userTwo);
        list.add(userThree);

        when(userRepository.findAll()).thenReturn(list);

        //test
        Set<UserResponseDto> actualUserList = underTest.getAllUsers();

        assertEquals(3, actualUserList.size());
        verify(userRepository, times(1)).findAll();
    }

    //create user
    @Test
    void testCanCreateUser() {
        //given
        UserRequestDto requestDto = validUserRequestDto();
        User user = new User("Baba", "Meca", "meca@abv.bg", passwordEncoder.encode("1234"));
        //when

        when(roleService.getRoleByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        underTest.registerUser(requestDto);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());

//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        User user = verify(userRepository).save(userArgumentCaptor.capture());

//        User user = new User("Danko", "Bahcheto","danko@abv.bg",passwordEncoder.encode("1234"));
//        user.setRoles(Set.of(roleService.getRoleByName("ROLE_USER")));
//        assertThat(userMapper.mapUserToUserDto(underTest.registerUser(requestDto))).isEqualTo(user);
    }

    //update
    @Test
    void testUpdateUserShouldThrowNotFoundException() {
        UserRequestDto userRequestDto =validUserRequestDto();
        assertThrows(UserNotFoundException.class, () ->
                this.underTest.updateUserById(1,"dinko@abv.bg", userRequestDto));
    }

    //update
    @Test
    @Disabled
    void testUpdateShouldWork() {
//        User user = getValidUser();
//
//        user.setId(23);
//
//        user.setFirstName("Test Name");
//        UserRequestDto newUser = new UserRequestDto();
//        user.setFirstName("New Test Name");
//        when(roleService.g)
//        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
//        underTest.updateUserById(user.getId(),user.getEmail(), newUser);
//        verify(userRepository).save(user);
//        verify(userRepository).findById(user.getId());
    }

    //delete
    @Test
    void testDeleteUserShouldDelete() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        underTest.deleteUserById(1);
        verify(userRepository).deleteById(1);
    }

    //delete
    @Test()
    void testExceptionShouldBeThrownWhenUpdateUserWithNonExistentId() {
        //given
        when(userRepository.findById(23)).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> underTest.deleteUserById(23));
    }

    private UserRequestDto validUserRequestDto() {
        return new UserRequestDto() {{
            setEmail("meca@abv.bg");
            setFirstName("Baba");
            setLastName("Meca");
            setPassword("1234");
        }};
    }
//    private UserRequestDto invalidUserRequestDto() {
//        return new UserRequestDto() {{
//            setEmail("dinko@abv.bg");
//            setFirstName("Danko");
//            setLastName("Bahcheto");
//            setPassword("1234");
//        }};
//    }
//
//    private User getValidUser() {
//        return new User() {{
//            setEmail("dinko@abv.bg");
//            setFirstName("Danko");
//            setLastName("Bahcheto");
//            setPassword("1234");
//        }};
//    }
}
