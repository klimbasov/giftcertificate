package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInputDto {
    long userId;
    long certificateId;
}
