package com.epam.esm.service.util;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.CertificateDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CertificateDtoEntityMapper {
    public static Certificate mapToEntity(CertificateDto certificateDto){
        Certificate.CertificateBuilder builder = Certificate.builder();

        return buildCertificate(certificateDto, builder);
    }

    public static Certificate mapToEntity(CertificateDto certificateDto, Certificate certificate){
        Certificate.CertificateBuilder builder = certificate.toBuilder();

        return buildCertificate(certificateDto, builder);
    }

    public static CertificateDto mapToDto(Certificate certificate, List<Tag> tags){
        return CertificateDto.builder()
                .id(certificate.getId())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .createDate(certificate.getCreateDate())
                .lastUpdateDate(certificate.getLastUpdateDate())
                .duration(certificate.getDuration())
                .tags(tags.stream().map(tag->tag.getName()).collect(Collectors.toList()))
                .build();
    }

    private static Certificate buildCertificate(CertificateDto certificateDto, Certificate.CertificateBuilder builder) {
        if (Objects.nonNull(certificateDto.getName())) {
            builder.name(certificateDto.getName());
        }
        if (Objects.nonNull(certificateDto.getDescription())) {
            builder.description(certificateDto.getDescription());
        }
        if (Objects.nonNull(certificateDto.getPrice())) {
            builder.price(certificateDto.getPrice());
        }
        if (Objects.nonNull(certificateDto.getCreateDate())) {
            builder.createDate(certificateDto.getCreateDate());
        }
        if (Objects.nonNull(certificateDto.getLastUpdateDate())) {
            builder.lastUpdateDate(certificateDto.getLastUpdateDate());
        }
        if (Objects.nonNull(certificateDto.getDuration())) {
            builder.duration(certificateDto.getDuration());
        }
        return builder.build();
    }

}
