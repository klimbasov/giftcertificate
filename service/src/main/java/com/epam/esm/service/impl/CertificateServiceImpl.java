package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectAlreadyExistException;
import com.epam.esm.service.util.mapper.Mapper;
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
import static com.epam.esm.service.util.pagination.Pager.toPage;
import static com.epam.esm.service.util.pagination.validator.PageValidator.validate;
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
    private final Mapper<Certificate, CertificateDto> mapper;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao, Mapper<Certificate, CertificateDto> mapper) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.mapper = mapper;
    }

    public static void mapToEntity(CertificateDto certificateDto, Certificate certificate) {
        if (nonNull(certificateDto.getName())) {
            certificate.setName(certificateDto.getName());
        }
        if (nonNull(certificateDto.getDescription())) {
            certificate.setDescription(certificateDto.getDescription());
        }
        if (certificateDto.getDuration() != 0) {
            certificate.setDuration(certificateDto.getDuration());
        }
        if (certificateDto.getPrice() != 0) {
            certificate.setPrice(certificateDto.getPrice());
        }
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        validateCreate(certificateDto);

        Certificate certificate = mapper.mapToEntity(certificateDto);
        Set<Tag> tags = syncTags(certificateDto);
        certificate.setTags(tags);
        Certificate createdCertificate = certificateDao.create(certificate)
                .orElseThrow(() -> new ObjectAlreadyExistException(OBJECT_ALREADY_EXISTS));
        return mapper.mapToModel(createdCertificate);
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
        return mapper.mapToModel(certificate);
    }

    @Override
    public CertificateDto read(Long id) {
        validateRead(id);

        Certificate certificate = certificateDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToModel(certificate);
    }

    @Override
    public void delete(Long id) {
        validateDelete(id);
        if (certificateDao.delete(id) == 0) {
            throw new NoSuchObjectException(NO_SUCH_OBJECT);
        }
    }

    @Override
    public PagedModel<CertificateDto> read(SearchOptions options, String[] tags) {
        validateRead(options);

        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();
        int offset = pageSize * (options.getPageNumber() - 1);
        String name = options.getSubname();
        String description = options.getSubdescription();

        long totalElements = certificateDao.count(name, description, tags);
        validate(totalElements, pageSize, pageNumber);
        List<Certificate> certificateList = certificateDao.read(name, description, tags, offset, options.getPageSize(), isSortingInverted(options));
        return toPage(mapper.mapToModels(certificateList), pageNumber, pageSize, totalElements);
    }

    private boolean isSortingInverted(SearchOptions searchOptions) {
        return getSortingDirectionByAlias(searchOptions.getSorting()) == SortingDirection.INCR;
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
                        .orElseThrow(() -> new ObjectAlreadyExistException(OBJECT_ALREADY_EXISTS)));
    }
}
