package com.epam.esm.domain.dto;

import com.epam.esm.service.constant.permissions.Permission;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RoleDto extends RepresentationModel<RoleDto> {
    long id;
    String name;
    Set<Permission> permissions;
}
