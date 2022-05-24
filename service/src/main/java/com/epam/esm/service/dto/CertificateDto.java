package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.sql.Date;
import java.util.List;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class CertificateDto {
    Integer id;
    List<String> tags;
    String name;
    String description;
    Float price;
    Integer duration;
    Date createDate;
    Date lastUpdateDate;
}
