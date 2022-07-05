package com.epam.esm.service.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagDto extends RepresentationModel<TagDto> {
    @Builder.Default
    long id = 0;
    @Builder.Default
    String name = null;
}
