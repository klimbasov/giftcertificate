package com.epam.esm.service.dto;

import lombok.*;

import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagDto implements Serializable {
    @Builder.Default
    Integer id = null;
    @Builder.Default
    String name = null;
}
