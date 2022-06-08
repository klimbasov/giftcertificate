package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Entity;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectCanNotBeCreatedException;
import com.epam.esm.service.util.sorting.Sorter;
import com.epam.esm.service.util.sorting.SortingDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public static void sort(List<Certificate> list, SortingDirection direction) {
        Sorter.sort(list, direction, Comparator.comparing(Certificate::getName));
    }

    @Override
    public CertificateDto add(CertificateDto certificateDto) throws ObjectCanNotBeCreatedException {
        validateCreate(certificateDto);
        Certificate certificate = mapToEntity(certificateDto);
        List<String> tagNames = certificateDto.getTags();
        Set<Tag> tagSet = addTags(tagNames);
        Set<Integer> tagIdSet = tagSet.stream()
                .map(Entity::getId)
                .collect(Collectors.toSet());
        int id = certificateDao.create(certificate, tagIdSet).getId();
        return mapToDto(certificate.toBuilder().id(id).build(), tagSet);
    }

    @Override
    public void put(CertificateDto certificateDto) throws NoSuchObjectException, ObjectCanNotBeCreatedException {
        validateUpdatePreMap(certificateDto);
        int id = certificateDto.getId();
        Certificate oldCertificate = certificateDao.read(id)
                .orElseThrow(() -> new NoSuchObjectException("No such object ot update."));
        Set<Integer> tagIds = getTagsIds(certificateDto);
        Certificate certificate = mapToEntity(certificateDto, oldCertificate);
        certificateDao.update(certificate, tagIds);
    }

    @Override
    public CertificateDto get(Integer id) throws NoSuchObjectException {
        validateRead(id);
        Certificate certificate = certificateDao.read(id).orElseThrow(NoSuchObjectException::new);
        Set<Tag> tags = tagDao.readByCertificateId(id);
        return mapToDto(certificate, tags);
    }

    @Override
    public void delete(Integer id) {
        validateDelete(id);
        certificateDao.delete(id);
    }

    @Override
    public List<CertificateDto> get(SearchOptions searchOptions) throws NoSuchObjectException {
        validateRead(searchOptions);
        List<Certificate> certificateList = certificateDao.read(searchOptions.getSubname(), searchOptions.getSubdescription(), "");
        throwIfEmpty(certificateList);
        sort(certificateList, getSortingDirectionByAlias(searchOptions.getSorting()));
        List<Set<Tag>> certificateTagList = getCertificatesTags(certificateList);
        return bunchMapToDto(certificateList, certificateTagList);
    }

    private List<Set<Tag>> getCertificatesTags(List<Certificate> certificateList) {
        return certificateList.stream().map(certificate -> tagDao.readByCertificateId(certificate.getId())).collect(Collectors.toList());
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

    private Set<Integer> getTagsIdsInternal(@NonNull CertificateDto certificateDto) throws ObjectCanNotBeCreatedException {
        Set<Integer> tagIds = new LinkedHashSet<>();
        for (String name : certificateDto.getTags()) {
            List<Tag> suchNamedTags = tagDao.read(name);
            if (suchNamedTags.isEmpty()) {
                try {
                    tagIds.add(tagDao.create(Tag.builder().name(name).build()).getId());
                } catch (DaoException e) {
                    throw new ObjectCanNotBeCreatedException(e);
                }
            } else {
                tagIds.add(suchNamedTags.get(0).getId());
            }
        }
        return tagIds;
    }

    private boolean certificateDtoHasTagList(@NonNull CertificateDto certificateDto) {
        return nonNull(certificateDto.getTags());
    }

    private Set<Integer> getTagsIds(@NonNull CertificateDto certificateDto) throws ObjectCanNotBeCreatedException {
        Set<Integer> tagIds = new LinkedHashSet<>();
        if (certificateDtoHasTagList(certificateDto)) {
            tagIds = getTagsIdsInternal(certificateDto);
        }
        return tagIds;
    }

    private Set<Tag> addTags(@NonNull List<String> tagNames) throws ObjectCanNotBeCreatedException {
        Set<Tag> tagSet = new LinkedHashSet<>();
        for (String name : tagNames) {
            Tag tag = spotOrAddTag(name);
            tagSet.add(tag);
        }
        return tagSet;
    }

    private Tag spotOrAddTag(@NonNull String name) throws ObjectCanNotBeCreatedException {
        Tag tag;
        List<Tag> tags = tagDao.read(name);
        if (!tags.isEmpty()) {
            tag = tags.get(0);
        } else {
            try {
                tag = tagDao.create(new Tag(name));
            } catch (DaoException e) {
                throw new ObjectCanNotBeCreatedException(e);
            }
        }
        return tag;
    }

    private void throwIfEmpty(@NonNull Collection<?> collection) {
        if (collection.isEmpty()) {
            throw new NoSuchObjectException();
        }
    }
}
