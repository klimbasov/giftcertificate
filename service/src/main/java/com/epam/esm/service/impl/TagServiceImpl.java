package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectAlreadyExist;
import com.epam.esm.service.util.mapper.TagDtoEntityMapper;
import com.epam.esm.service.util.sorting.SortingDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
import static com.epam.esm.service.constant.ExceptionMessages.OBJECT_ALREADY_EXISTS;
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

    @Override
    public TagDto create(TagDto tagDto) {
        validateCreate(tagDto);
        String tagName = tagDto.getName();
        Tag tag = tagDao.create(new Tag(tagName))
                .orElseThrow(() -> new ObjectAlreadyExist(OBJECT_ALREADY_EXISTS));
        return mapToDto(tag);
    }

    @Override
    public TagDto read(Long id) {
        validateRead(id);
        Tag tag = tagDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapToDto(tag);
    }

    @Override
    public PagedModel<TagDto> read(SearchOptions options) {
        validateRead(options);
        int offset = options.getPageSize() * (options.getPageNumber() - 1);
        List<Tag> dtoList = tagDao.read(options.getSubname(), offset, options.getPageSize(), isSortingInverted(options));
        long totalElements = tagDao.count(options.getSubname());
        return PagedModel.of(dtoList.stream().map(TagDtoEntityMapper::mapToDto).collect(Collectors.toList()), new PagedModel.PageMetadata(options.getPageSize(), options.getPageNumber(), totalElements));
    }

    @Override
    public void delete(Long id) {
        validateDelete(id);
        throwIfNoEffect(tagDao.delete(id));
    }

    private boolean isSortingInverted(SearchOptions searchOptions) {
        return getSortingDirectionByAlias(searchOptions.getSorting()) == SortingDirection.INCR;
    }

    private void throwIfNoEffect(int modifiedLines) {
        if (modifiedLines == 0) {
            throw new NoSuchObjectException(NO_SUCH_OBJECT);
        }
    }
}
