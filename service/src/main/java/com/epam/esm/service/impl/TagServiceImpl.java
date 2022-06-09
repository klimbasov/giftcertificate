package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.TagDtoEntityMapper;
import com.epam.esm.service.util.sorting.Sorter;
import com.epam.esm.service.util.sorting.SortingDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
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

    private static void sort(List<Tag> list, SortingDirection direction) {
        Sorter.sort(list, direction, Comparator.comparing(Tag::getName));
    }

    @Override
    public TagDto add(TagDto tagDto) {
        validateCreate(tagDto);
        String tagName = tagDto.getName();
        Optional<Tag> optionalSpottedTag = tagDao.read(tagName).stream()
                .filter(tag1 -> tag1.getName().equals(tagName))
                .findAny();
        Tag tag = optionalSpottedTag.orElseGet(() -> tagDao.create(new Tag(tagName)));
        return mapToDto(tag);
    }

    @Override
    public TagDto get(Integer id) {
        validateRead(id);
        Tag tag = tagDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapToDto(tag);
    }

    @Override
    public List<TagDto> get(SearchOptions options) {
        validateRead(options);
        List<Tag> dtoList = tagDao.read(options.getSubname());
        sort(dtoList, getSortingDirectionByAlias(options.getSorting()));
        return dtoList.stream().map(TagDtoEntityMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        validateDelete(id);
        throwIfNoEffect(tagDao.delete(id));
    }

    private void throwIfNoEffect(int modifiedLines) {
        if (modifiedLines == 0) {
            throw new NoSuchObjectException(NO_SUCH_OBJECT);
        }
    }
}
