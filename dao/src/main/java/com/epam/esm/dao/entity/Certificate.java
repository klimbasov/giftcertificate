package com.epam.esm.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Value
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Certificate extends Entity {
    @NonNull
    String name;
    @NonNull
    String description;
    @NonNull
    Float price;
    @NonNull
    Integer duration;
    @NonNull
    LocalDateTime createDate;
    @NonNull
    LocalDateTime lastUpdateDate;
}
