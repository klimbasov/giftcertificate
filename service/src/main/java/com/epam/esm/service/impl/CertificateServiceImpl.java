package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectAlreadyExist;
import com.epam.esm.service.util.mapper.CertificateDtoEntityMapper;
import com.epam.esm.service.util.sorting.SortingDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
import static com.epam.esm.service.constant.ExceptionMessages.OBJECT_ALREADY_EXISTS;
import static com.epam.esm.service.util.mapper.CertificateDtoEntityMapper.mapToDto;
import static com.epam.esm.service.util.mapper.CertificateDtoEntityMapper.mapToEntity;
import static com.epam.esm.service.util.sorting.SortingDirection.getSortingDirectionByAlias;
import static com.epam.esm.service.util.validator.ArgumentValidator.CertificateDtoValidator.validateCreate;
import static com.epam.esm.service.util.validator.ArgumentValidator.CertificateDtoValidator.validateUpdatePreMap;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateDelete;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateRead;
import static java.util.Objects.nonNull;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;
    private final TagDao tagDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        validateCreate(certificateDto);
        Certificate certificate = mapToEntity(certificateDto);
        Set<Tag> tags = syncTags(certificateDto);
        certificate.setTags(tags);
        Certificate createdCertificate = certificateDao.create(certificate)
                .orElseThrow(() -> new ObjectAlreadyExist(OBJECT_ALREADY_EXISTS));
        return mapToDto(createdCertificate);
    }

    @Override
    public CertificateDto update(CertificateDto certificateDto) {
        validateUpdatePreMap(certificateDto);
        Certificate certificate = certificateDao.read(certificateDto.getId())
                .orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        Set<Tag> tags = syncTags(certificateDto, certificate);
        certificate.setTags(tags);
        mapToEntity(certificateDto, certificate);
        certificateDao.update(certificate);
        return mapToDto(certificate);
    }

    @Override
    public CertificateDto read(Long id) {
        validateRead(id);
        Certificate certificate = certificateDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapToDto(certificate);
    }

    @Override
    public void delete(Long id) {
        validateDelete(id);
        throwIfNoEffect(certificateDao.delete(id));
    }

    @Override
    public PagedModel<CertificateDto> read(SearchOptions searchOptions, String[] tags) {
        validateRead(searchOptions);
        int offset = getOffset(searchOptions);
        List<Certificate> certificateList = certificateDao.read(searchOptions.getSubname(), searchOptions.getSubdescription(), tags, offset, searchOptions.getPageSize(), isSortingInverted(searchOptions));
        long totalElements = certificateDao.count(searchOptions.getSubname(), searchOptions.getSubdescription(), tags);
        return PagedModel.of(bunchMapToDto(certificateList), new PagedModel.PageMetadata(searchOptions.getPageSize(), searchOptions.getPageNumber(), totalElements));
    }

    private boolean isSortingInverted(SearchOptions searchOptions) {
        return getSortingDirectionByAlias(searchOptions.getSorting()) == SortingDirection.INCR;
    }

    private int getOffset(SearchOptions searchOptions) {
        return (searchOptions.getPageNumber() - 1) * searchOptions.getPageSize();
    }

    private List<CertificateDto> bunchMapToDto(@NonNull List<Certificate> certificateList) {
        return certificateList.stream().map(CertificateDtoEntityMapper::mapToDto).collect(Collectors.toList());
    }

    private Set<Tag> syncTags(@NonNull CertificateDto certificateDto, @NonNull Certificate oldCertificate) {
        return nonNull(certificateDto.getTags()) ? getTagsIdsInternal(certificateDto) : oldCertificate.getTags();
    }

    private Set<Tag> syncTags(@NonNull CertificateDto certificateDto) {
        return nonNull(certificateDto.getTags()) ? getTagsIdsInternal(certificateDto) : new HashSet<>();
    }

    private Set<Tag> getTagsIdsInternal(@NonNull CertificateDto certificateDto) {
        return certificateDto.getTags().stream().map(this::spotOrAddTag).collect(Collectors.toSet());
    }

    private Tag spotOrAddTag(@NonNull String name) {
        return tagDao.read(name, 0, Integer.MAX_VALUE, false).stream()
                .filter(tag -> tag.getName().equals(name))
                .findAny()
                .orElseGet(() -> tagDao.create(new Tag(name))
                        .orElseThrow(() -> new ObjectAlreadyExist(OBJECT_ALREADY_EXISTS)));
    }

    private void throwIfNoEffect(int modifiedLines) {
        if (modifiedLines == 0) {
            throw new NoSuchObjectException();
        }
    }
}
