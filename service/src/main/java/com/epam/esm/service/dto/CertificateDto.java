package com.epam.esm.service.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CertificateDto extends RepresentationModel<CertificateDto> {
    @Builder.Default
    long id = 0;
    @Builder.Default
    String name = null;
    @Builder.Default
    String description = null;
    @Builder.Default
    double price = 0;
    @Builder.Default
    int duration = 0;
    @Builder.Default
    String createDate = null;
    @Builder.Default
    String lastUpdateDate = null;
    @Builder.Default
    List<String> tags = null;
}
