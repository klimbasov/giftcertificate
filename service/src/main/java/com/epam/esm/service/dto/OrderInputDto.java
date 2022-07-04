package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class OrderInputDto {
    long userId = 0;
    long certificateId = 0;
}
