package com.epam.esm.domain.mapper.impl;

import com.epam.esm.dao.entity.Role;
import com.epam.esm.service.constant.permissions.Permission;
import com.epam.esm.service.dto.RoleDto;
import com.epam.esm.service.util.mapper.Mapper;
import com.epam.esm.service.util.mapper.exception.MappingException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleDtoEntityMapper implements Mapper<Role, RoleDto> {
    @Override
    public RoleDto mapToModel(Role entity) {
        Set<Permission> permissions = entity.getPermissions().stream().parallel()
                .map(aByte -> Permission.getById(aByte).orElseThrow(()->new MappingException("Invalid permission identifier met.")))
                .collect(Collectors.toSet());
        return new RoleDto(entity.getId(), entity.getName(), permissions);
    }

    @Override
    public Role mapToEntity(RoleDto model) {
        Set<Byte> permissionsId = model.getPermissions().stream().map(Permission::getId).collect(Collectors.toSet());
        return new Role(0L, model.getName(),new HashSet<>(), permissionsId);
    }

    @Override
    public List<Role> mapToEntities(List<RoleDto> models) {
        return models.stream().map(this::mapToEntity).collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> mapToModels(List<Role> entities) {
        return entities.stream().map(this::mapToModel).collect(Collectors.toList());
    }
}
