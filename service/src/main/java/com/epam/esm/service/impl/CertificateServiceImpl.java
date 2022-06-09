package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Entity;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.sorting.Sorter;
import com.epam.esm.service.util.sorting.SortingDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
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

    private static void sort(List<Certificate> list, SortingDirection direction) {
        Sorter.sort(list, direction, Comparator.comparing(Certificate::getName));
    }

    @Override
    public CertificateDto add(CertificateDto certificateDto) {
        validateCreate(certificateDto);
        Certificate certificate = mapToEntity(certificateDto);
        Set<Tag> tagSet = addTags(certificateDto.getTags());
        Set<Integer> tagIdSet = tagSet.stream()
                .map(Entity::getId)
                .collect(Collectors.toSet());
        int id = certificateDao.create(certificate, tagIdSet).getId();
        return mapToDto(certificate.toBuilder().id(id).build(), tagSet);
    }

    @Override
    public void put(CertificateDto certificateDto) {
        validateUpdatePreMap(certificateDto);
        Certificate oldCertificate = certificateDao.read(certificateDto.getId())
                .orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        Set<Integer> tagIds = getTagsIds(certificateDto);
        Certificate certificate = mapToEntity(certificateDto, oldCertificate);
        certificateDao.update(certificate, tagIds);
    }

    @Override
    public CertificateDto get(Integer id) {
        validateRead(id);
        Certificate certificate = certificateDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        Set<Tag> tags = tagDao.readByCertificateId(id);
        return mapToDto(certificate, tags);
    }

    @Override
    public void delete(Integer id) {
        validateDelete(id);
        throwIfNoEffect(certificateDao.delete(id));
    }

    @Override
    public List<CertificateDto> get(SearchOptions searchOptions) {
        validateRead(searchOptions);
        List<Certificate> certificateList = certificateDao.read(searchOptions.getSubname(), searchOptions.getSubdescription(), "");
        sort(certificateList, getSortingDirectionByAlias(searchOptions.getSorting()));
        List<Set<Tag>> certificateTagList = getCertificatesTags(certificateList);
        return bunchMapToDto(certificateList, certificateTagList);
    }

    private List<Set<Tag>> getCertificatesTags(List<Certificate> certificateList) {
        return certificateList.stream()
                .map(certificate -> tagDao.readByCertificateId(certificate.getId()))
                .collect(Collectors.toList());
    }

    private List<CertificateDto> bunchMapToDto(@NonNull List<Certificate> certificateList, @NonNull List<Set<Tag>> certificateTagList) {
        ListIterator<Certificate> certificateListIterator = certificateList.listIterator();
        ListIterator<Set<Tag>> certificateTagListIterator = certificateTagList.listIterator();
        List<CertificateDto> dtoList = new LinkedList<>();
        while (certificateListIterator.hasNext() && certificateTagListIterator.hasNext()) {
            CertificateDto dto = mapToDto(certificateListIterator.next(), certificateTagListIterator.next());
            dtoList.add(dto);
        }
        return dtoList;
    }

    private Set<Integer> getTagsIdsInternal(@NonNull CertificateDto certificateDto) {
        Set<Integer> tagIds = new LinkedHashSet<>();
        for (String name : certificateDto.getTags()) {
            List<Tag> suchNamedTags = tagDao.read(name);
            Tag tag = suchNamedTags.stream().findAny().orElseGet(() -> tagDao.create(Tag.builder().name(name).build()));
            tagIds.add(tag.getId());
        }
        return tagIds;
    }

    private boolean certificateDtoHasTagList(@NonNull CertificateDto certificateDto) {
        List<String> tags = certificateDto.getTags();
        return nonNull(tags) && !tags.isEmpty();
    }

    private Set<Integer> getTagsIds(@NonNull CertificateDto certificateDto) {
        return certificateDtoHasTagList(certificateDto) ? getTagsIdsInternal(certificateDto) : new HashSet<>();
    }

    private Set<Tag> addTags(@NonNull List<String> tagNames) {
        return tagNames.stream().map(this::spotOrAddTag).collect(Collectors.toSet());
    }

    private Tag spotOrAddTag(@NonNull String name) {
        return tagDao.read(name).stream().findAny().orElseGet(() -> tagDao.create(new Tag(name)));
    }

    private void throwIfNoEffect(int modifiedLines) {
        if (modifiedLines == 0) {
            throw new NoSuchObjectException();
        }
    }
}
