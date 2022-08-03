package com.epam.esm.domain.mapper.impl;

import com.epam.esm.dao.entity.Role;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.constant.permissions.Permission;
import com.epam.esm.service.util.mapper.Mapper;
import com.epam.esm.service.util.mapper.exception.MappingException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDetailEntityMapper implements Mapper<User, UserDetails> {
    @Override
    public UserDetails mapToModel(User entity) {
        Set<GrantedAuthority> authorities = entity.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(Collection::stream)
                .distinct().map(aByte -> Permission.getById(aByte).orElseThrow(MappingException::new))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                entity.getName(),
                entity.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }

    @Override
    public User mapToEntity(UserDetails model) {
        return null;
    }

    @Override
    public List<User> mapToEntities(List<UserDetails> models) {
        return null;
    }

    @Override
    public List<UserDetails> mapToModels(List<User> entities) {
        return null;
    }
}
