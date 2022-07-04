package com.epam.esm.service.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto extends RepresentationModel<UserDto> {
    @Builder.Default
    long id = 0;
    @Builder.Default
    String name = null;
    @Builder.Default
    List<OrderDto> orders = null;
}
