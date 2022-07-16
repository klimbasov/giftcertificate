package com.epam.esm.service.util.mapper;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;

public class TagDtoEntityMapper {

    private TagDtoEntityMapper() {
    }

    public static TagDto mapToDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }
}
