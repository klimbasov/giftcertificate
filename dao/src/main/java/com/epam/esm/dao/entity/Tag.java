package com.epam.esm.dao.entity;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@ToString
public class Tag extends Entity {
    @NonNull
    String name;
}