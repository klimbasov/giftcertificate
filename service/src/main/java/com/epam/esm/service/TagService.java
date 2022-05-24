package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagService {
    TagDto add(TagDto tagDto);

    Optional<TagDto> get(Integer id);

    void delete(Integer id);

    Optional<List<TagDto>> get(Map<String, String> options);
}
