package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class TagDto implements Serializable {
    @NonNull
    Integer id;
    @NonNull
    String name;
}
