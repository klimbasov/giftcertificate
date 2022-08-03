package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectAlreadyExistException;
import com.epam.esm.service.util.mapper.Mapper;
import com.epam.esm.service.util.sorting.SortingDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
import static com.epam.esm.service.constant.ExceptionMessages.OBJECT_ALREADY_EXISTS;
import static com.epam.esm.service.util.pagination.Pager.toPage;
import static com.epam.esm.service.util.pagination.validator.PageValidator.validate;
import static com.epam.esm.service.util.sorting.SortingDirection.getSortingDirectionByAlias;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;
import static com.epam.esm.service.util.validator.ArgumentValidator.TagDtoValidator.validateCreate;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateDelete;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateRead;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao dao;
    private final Mapper<Tag, TagDto> mapper;

    @Autowired
    public TagServiceImpl(TagDao tagDao, Mapper<Tag, TagDto> mapper) {
        this.dao = tagDao;
        this.mapper = mapper;
    }

    @Override
    public TagDto create(TagDto dto) {
        validateCreate(dto);
        String tagName = dto.getName();
        Tag tag = dao.create(new Tag(tagName))
                .orElseThrow(() -> new ObjectAlreadyExistException(OBJECT_ALREADY_EXISTS));
        return mapper.mapToModel(tag);
    }

    @Override
    public TagDto read(Long id) {
        validateRead(id);
        Tag entity = dao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToModel(entity);
    }

    @Override
    public PagedModel<TagDto> read(SearchOptions options) {
        validateRead(options);

        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();
        int offset = pageSize * (options.getPageNumber() - 1);

        long totalElements = dao.count(options.getSubname());
        validate(totalElements, pageSize, pageNumber);
        List<Tag> entities = dao.read(options.getSubname(), offset, options.getPageSize(), isSortingInverted(options));
        return toPage(mapper.mapToModels(entities), pageNumber, pageSize, totalElements);
    }

    @Override
    public TagDto readMostUsedTagOfUserWithHighestOrderCost() {
        Tag entity = dao.readMostUsedTagOfUserWithHighestOrderCost().orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToModel(entity);
    }

    @Override
    public void delete(Long id) {
        validateDelete(id);
        throwIfNoEffect(dao.delete(id));
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
