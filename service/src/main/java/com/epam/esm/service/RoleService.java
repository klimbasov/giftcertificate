package com.epam.esm.service;

import com.epam.esm.service.dto.RoleDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import org.springframework.hateoas.PagedModel;

/**
 * Specifies a read business logic.
 */
public interface RoleService {
    /**
     * Issue a read-by-option operation.
     *
     * @param options - object, containing options, the search entity must conform to.
     * @return - list of fully initialized dtos, conformed to provided options.
     */
    PagedModel<RoleDto> read(SearchOptions options);

    /**
     * Issue a delete operation.
     *
     * @param id - id value, associated with an object.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    RoleDto read(long id);
}
