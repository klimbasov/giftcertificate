package com.epam.esm.service.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDto extends RepresentationModel<OrderDto> {
    @Builder.Default
    long id = 0;
    @Builder.Default
    String timestamp = null;
    @Builder.Default
    long certificateId = 0;
    @Builder.Default
    double cost = 0;
    @Builder.Default
    long userId = 0;
}
