package com.epam.esm.dao.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Entity {
    @Builder.Default
    Integer id = 0;
}
