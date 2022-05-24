package com.epam.esm.dao.entity;

import lombok.*;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@ToString
@SuperBuilder(toBuilder = true)
public class Entity {
    @Builder.Default
    Integer id = 0;
}
