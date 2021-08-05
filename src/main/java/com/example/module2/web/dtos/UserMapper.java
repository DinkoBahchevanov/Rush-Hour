package com.example.module2.web.dtos;

import com.example.module2.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target="firstName", source="userRequestDto.firstName"),
            @Mapping(target="lastName", source="userRequestDto.lastName"),
            @Mapping(target="email", source="userRequestDto.email"),
            @Mapping(target="password", source="userRequestDto.password"),
            @Mapping(target = "roles", ignore = true)
    })
    User mapUserDtoToUser(UserRequestDto userRequestDto);

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target="firstName", source="user.firstName"),
            @Mapping(target="lastName", source="user.lastName"),
            @Mapping(target="email", source="user.email"),
    })
    UserResponseDto mapUserToUserDto(User user);
}
