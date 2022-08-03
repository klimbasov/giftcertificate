package com.epam.esm.domain.mapper.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.util.mapper.Mapper;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoEntityMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto mapToModel(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    @Override
    public User mapToEntity(UserDto model) {
        throw new NotImplementedException();
    }

    @Override
    public List<User> mapToEntities(List<UserDto> models) {
        throw new NotImplementedException();
    }

    @Override
    public List<UserDto> mapToModels(List<User> users) {
        return users.stream().map(this::mapToModel).collect(Collectors.toList());
    }
}
