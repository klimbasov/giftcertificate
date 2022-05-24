package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class TagDto {
    @NonNull
    Integer id;
    @NonNull
    String name;
}
