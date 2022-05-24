package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Entity;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.NoSuchObjectException;
import com.epam.esm.service.util.CertificateDtoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.esm.service.util.CertificateDtoEntityMapper.mapToEntity;

@Service
public class CertificateServiceImpl implements CertificateService {

    CertificateDao certificateDao;
    TagDao tagDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao){
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
    }

    @Override
    public int add(CertificateDto certificateDto) {
        Certificate certificate = mapToEntity(certificateDto);
        List<String> tagNames = certificateDto.getTags();
        Set<Integer> tagIdSet = addTags(tagNames).stream()
                .map(Entity::getId)
                .collect(Collectors.toSet());
        return certificateDao.create(certificate, tagIdSet).getId();
    }

    private Set<Tag> addTags(List<String> tagNames) {
        return tagNames.stream()
                .map(Tag::new)
                .map(tagDao::create)
                .collect(Collectors.toSet());
    }

    @Override
    public void put(CertificateDto certificateDto) throws NoSuchObjectException {
        int id = certificateDto.getId();
        Certificate oldCertificate = certificateDao.read(id)
                .orElseThrow(()->new NoSuchObjectException("No such object ot update."));
        Set<Integer> tagIds = certificateDto.getTags().stream()
                .map(name -> tagDao.read(name)
                    .orElseGet(LinkedList<Tag>::new).stream()
                    .filter(tag -> name.equals(tag.getName()))
                    .findAny()
                    .orElseGet(()->tagDao.create(new Tag(name))).getId())
                .collect(Collectors.toSet());
        Certificate certificate = mapToEntity(certificateDto, oldCertificate);
        certificateDao.update(certificate, tagIds);
    }

    private void createMtmDependency(int id, List<Integer> tagIdList) {


        certificateTagDao.delete(id);
        tagIdList.forEach(tagId ->{certificateTagDao.create(id, tagId);});
    }

    private List<Integer> createTags(List<String> tagList) {
        List<Integer> tagIdList = new ArrayList<>();
        if(Objects.nonNull(tagList)){
            tagIdList = tagList.stream().map(name->{return tagDao.create(new Tag(name));}).collect(Collectors.toList());
        }
        return tagIdList;
    }

    private void putCertificate(CertificateDto certificateDto, Optional<Entity<Certificate>> optionalEntity) {
        if(optionalEntity.isPresent()){
            Certificate certificate = optionalEntity.get().getInstance();
            Certificate newCertificate = mapToEntity(certificateDto, certificate);
            certificateDao.update(newCertificate, certificateDto.getId());
        }
    }

    @Override
    public Optional<CertificateDto> get(Integer id) {
        CertificateDto certificateDto = null;

        Optional<Entity<Certificate>> optionalCertificateEntity = certificateDao.read(id);
        Optional<List<Entity<Tag>>> optionalTagEntities = certificateTagDao.readTags(id);
        if(optionalCertificateEntity.isPresent()){
            certificateDto = CertificateDtoEntityMapper.mapToDto(
                    optionalCertificateEntity.get(),
                    optionalTagEntities.orElse(new ArrayList<>()).stream().map(tagEntity -> tagEntity.getInstance()).collect(Collectors.toList()));
        }
        return Optional.ofNullable(certificateDto);
    }

    @Override
    public void delete(Integer id) {
        certificateDao.delete(id);
    }

    @Override
    public Optional<List<CertificateDto>> get(SearchOptions searchOptions) {
        Optional<List<Entity<Certificate>>> optionalEntityList = certificateDao.read(searchOptions.getSORTING(), searchOptions.getSUBNAME(), searchOptions.getSUBDESCRIPTION());
        List<CertificateDto> certificateDtoList = new LinkedList<>();
        if(optionalEntityList.isPresent()){
            certificateDtoList = optionalEntityList.get().map(list->{})
        }

    }
}
