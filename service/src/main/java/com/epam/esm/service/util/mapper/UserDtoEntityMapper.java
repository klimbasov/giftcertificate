package com.epam.esm.service.util.mapper;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoEntityMapper {

    private final OrderDtoEntityMapper orderMapper;

    @Autowired
    private UserDtoEntityMapper(OrderDtoEntityMapper mapper) {
        this.orderMapper = mapper;
    }

    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .orders(user.getOrders().stream().map(orderMapper::mapToDto).collect(Collectors.toList()))
                .build();
    }

    public List<UserDto> mapToDto(List<User> users) {
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
