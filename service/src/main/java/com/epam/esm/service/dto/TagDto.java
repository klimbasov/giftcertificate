package com.epam.esm.service.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagDto extends RepresentationModel<TagDto> {
    @Builder.Default
    long id = 0;
    @Builder.Default
    String name = null;
}
