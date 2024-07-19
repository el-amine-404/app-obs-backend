package org.obs.app.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.obs.app.dto.UserDto;
import org.obs.app.model.User;

import java.util.Collections;
import java.util.List;

// do not use: @Mapper(componentModel = "cdi")
// source = https://github.com/quarkusio/quarkus/issues/32983
//@Mapper(componentModel = "cdi")
@ApplicationScoped
public class UserMapper {


    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());

        return user;
    }

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());

        return userDto;
    }


    public List<User> toEntityList(List<UserDto> userDtoList) {
        if (userDtoList == null) {
            return Collections.emptyList();
        }

        return userDtoList.stream()
                .map(this::toEntity)
                .toList();
    }


    public List<UserDto> toDtoList(List<User> userEntityList) {
        if (userEntityList == null) {
            return Collections.emptyList();
        }

        return userEntityList.stream()
                .map(this::toDto)
                .toList();
    }
}


