package com.epam.esm.domain.mapper.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.util.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagDtoEntityMapper implements Mapper<Tag, TagDto> {

    @Override
    public TagDto mapToModel(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    @Override
    public Tag mapToEntity(TagDto model) {
        return new Tag(0, model.getName(), new HashSet<>());
    }

    @Override
    public List<Tag> mapToEntities(List<TagDto> models) {
        return models.stream().map(this::mapToEntity).collect(Collectors.toList());
    }

    @Override
    public List<TagDto> mapToModels(List<Tag> entities) {
        return entities.stream().map(this::mapToModel).collect(Collectors.toList());
    }
}
