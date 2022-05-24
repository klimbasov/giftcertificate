package com.epam.esm.dao.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.List;

@Value
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Certificate extends Entity{
    @NonNull
    String name;
    @NonNull
    String description;
    @NonNull
    Float price;
    @NonNull
    Integer duration;
    @NonNull
    Date createDate;
    @NonNull
    Date lastUpdateDate;
}
