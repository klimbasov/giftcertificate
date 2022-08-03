package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.dao.entity.Role;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.dto.RoleDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;

import java.util.List;

import static com.epam.esm.service.util.pagination.Pager.createPageMetadata;
import static com.epam.esm.service.util.pagination.Pager.toPage;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final Mapper<Role, RoleDto> mapper;
    private final RoleDao dao;

    @Override
    public PagedModel<RoleDto> read(SearchOptions options) {
        validateRead(options);

        int offset = options.getPageSize() * (options.getPageNumber() - 1);

        long totalElements = dao.count(options.getSubname());
        List<Role> entities = dao.read(offset, options.getPageSize(), options.getSubname());
        PagedModel.PageMetadata metadata = createPageMetadata(options, totalElements);
        return toPage(mapper.mapToModels(entities), metadata);
    }

    @Override
    public RoleDto read(long id) {
        Role entity = dao.read(id).orElseThrow(() -> new NoSuchObjectException());
        return mapper.mapToModel(entity);

    }
}
