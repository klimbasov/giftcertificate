package com.epam.esm.service;

import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectCanNotBeCreatedException;

import java.util.List;

public interface TagService {
    TagDto add(TagDto tagDto) throws ObjectCanNotBeCreatedException;

    TagDto get(Integer id) throws NoSuchObjectException;

    List<TagDto> get(SearchOptions options) throws NoSuchObjectException;

    void delete(Integer id);
}
