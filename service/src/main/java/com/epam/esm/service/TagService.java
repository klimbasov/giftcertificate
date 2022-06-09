package com.epam.esm.service;

import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectCanNotBeCreatedException;

import java.util.List;

/**
 * Specifies a tag CRD business logic.
 */
public interface TagService {
    /**
     * Issue a tag create operation.
     *
     * @param tagDto - dto containing tag to be added.
     * @return fully initialized dto. Tag id is provided.
     * @throws ObjectCanNotBeCreatedException - if input dto state is invalid. For validation requirements see {@link com.epam.esm.service.util.validator.ArgumentValidator}
     */
    TagDto add(TagDto tagDto);

    /**
     * Issue a tag read-by-id operation.
     *
     * @param id - id value, associated with an object.
     * @return - fully initialized dto, associated with input id.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    TagDto get(Integer id);

    /**
     * Issue a tag read-by-option operation.
     *
     * @param options - object, containing options, the search certificates must conform to.
     * @return - list of fully initialized dtos, conformed to provided options.
     */
    List<TagDto> get(SearchOptions options);

    /**
     * Issue a tag delete operation.
     *
     * @param id - id value, associated with an object.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    void delete(Integer id);
}
