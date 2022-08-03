package com.epam.esm.domain.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto extends RepresentationModel<UserDto> {
    @Builder.Default
    long id = 0;
    @Builder.Default
    String name = null;
}
