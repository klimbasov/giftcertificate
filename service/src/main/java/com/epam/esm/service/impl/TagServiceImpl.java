package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectCanNotBeCreatedException;
import com.epam.esm.service.util.mapper.TagDtoEntityMapper;
import com.epam.esm.service.util.sorting.Sorter;
import com.epam.esm.service.util.sorting.SortingDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.service.util.mapper.TagDtoEntityMapper.mapToDto;
import static com.epam.esm.service.util.sorting.SortingDirection.getSortingDirectionByAlias;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;
import static com.epam.esm.service.util.validator.ArgumentValidator.TagDtoValidator.validateCreate;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateDelete;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateRead;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public static void sort(List<Tag> list, SortingDirection direction) {
        Sorter.sort(list, direction, Comparator.comparing(Tag::getName));
    }

    @Override
    public TagDto add(TagDto tagDto) throws ObjectCanNotBeCreatedException {
        validateCreate(tagDto);
        Tag tag;
        String tagName = tagDto.getName();
        Optional<Tag> optionalSpottedTag = tagDao.read(tagName).stream()
                .filter(tag1 -> tag1.getName().equals(tagName))
                .findAny();
        if (optionalSpottedTag.isPresent()) {
            tag = optionalSpottedTag.get();
        } else {
            try {
                tag = tagDao.create(new Tag(tagName));
            } catch (DaoException e) {
                throw new ObjectCanNotBeCreatedException(e);
            }
        }
        return mapToDto(tag);
    }

    @Override
    public TagDto get(Integer id) throws NoSuchObjectException {
        validateRead(id);
        Tag tag = tagDao.read(id).orElseThrow(NoSuchObjectException::new);
        return mapToDto(tag);
    }

    @Override
    public List<TagDto> get(SearchOptions options) throws NoSuchObjectException {
        validateRead(options);
        List<Tag> dtoList = tagDao.read(options.getSubname());
        throwIfObjectNotSpotted(dtoList);
        sort(dtoList, getSortingDirectionByAlias(options.getSorting()));
        return dtoList.stream().map(TagDtoEntityMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        validateDelete(id);
        tagDao.delete(id);
    }

    private void throwIfObjectNotSpotted(List<Tag> dtoList) {
        if (dtoList.isEmpty()) {
            throw new NoSuchObjectException();
        }
    }
}
