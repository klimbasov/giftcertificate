package com.epam.esm.service.util.mapper.impl;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.util.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.util.LocalDateTimeFormatter.toIso8601Str;
import static java.util.Objects.nonNull;

@Component
public class CertificateDtoEntityMapper implements Mapper<Certificate, CertificateDto> {

    private static Certificate.CertificateBuilder getNewCertificateBuilder() {
        return Certificate.builder()
                .isSearchable(true)
                .createDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .lastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    private static Certificate buildCertificate(CertificateDto certificateDto, Certificate.CertificateBuilder builder) {
        if (nonNull(certificateDto.getName())) {
            builder.name(certificateDto.getName());
        }
        if (nonNull(certificateDto.getDescription())) {
            builder.description(certificateDto.getDescription());
        }
        if (certificateDto.getDuration() != 0) {
            builder.duration(certificateDto.getDuration());
        }
        if (certificateDto.getPrice() != 0) {
            builder.price(certificateDto.getPrice());
        }
        return builder.build();
    }

    @Override
    public CertificateDto mapToModel(Certificate entity) {
        return CertificateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .createDate(toIso8601Str(entity.getCreateDate()))
                .lastUpdateDate(toIso8601Str(entity.getLastUpdateDate()))
                .duration(entity.getDuration())
                .tags(entity.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Certificate mapToEntity(CertificateDto certificateDto) {
        Certificate.CertificateBuilder builder = getNewCertificateBuilder();
        return buildCertificate(certificateDto, builder);
    }

    @Override
    public List<Certificate> mapToEntities(List<CertificateDto> models) {
        return models.stream().map(this::mapToEntity).collect(Collectors.toList());
    }

    @Override
    public List<CertificateDto> mapToModels(List<Certificate> entities) {
        return entities.stream().map(this::mapToModel).collect(Collectors.toList());
    }
}
