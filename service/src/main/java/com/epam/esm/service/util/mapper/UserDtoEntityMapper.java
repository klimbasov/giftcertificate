package com.epam.esm.service.util.mapper;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoEntityMapper {

    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public List<UserDto> mapToDto(List<User> users) {
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
