package com.epam.esm.dao.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends Entity {
    @NonNull
    String name;
}