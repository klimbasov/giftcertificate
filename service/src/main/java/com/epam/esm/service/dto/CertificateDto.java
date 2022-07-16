package com.epam.esm.service.dto;

import lombok.*;

import java.util.List;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CertificateDto {
    @Builder.Default
    Integer id = null;
    @Builder.Default
    String name = null;
    @Builder.Default
    String description = null;
    @Builder.Default
    Float price = null;
    @Builder.Default
    Integer duration = null;
    @Builder.Default
    String createDate = null;
    @Builder.Default
    String lastUpdateDate = null;
    @Builder.Default
    List<String> tags = null;
}
