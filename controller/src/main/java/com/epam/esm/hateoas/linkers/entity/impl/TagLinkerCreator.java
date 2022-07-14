package com.epam.esm.hateoas.linkers.entity.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.hateoas.linkers.entity.AbstractEntityLinkerCreator;
import com.epam.esm.service.dto.TagDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TagLinkerCreator extends AbstractEntityLinkerCreator<TagDto> {

    private static final String DELETE_METHOD_NAME = "delete";
    private static final String READ_BY_ID_METHOD_NAME = "readById";
    private static final String CREATE_METHOD_NAME = "create";

    private static final String DELETE_METHOD_RELATION = "delete";
    private static final String READ_BY_ID_METHOD_RELATION = "self";
    private static final String CREATE_METHOD_RELATION = "create";

    private final Method deleteMethod;
    private final Method readByIdMethod;
    private final Method createMethod;

    public TagLinkerCreator() {
        deleteMethod = ReflectionUtils.findMethod(TagController.class, DELETE_METHOD_NAME, Long.class);
        readByIdMethod = ReflectionUtils.findMethod(TagController.class, READ_BY_ID_METHOD_NAME, Long.class);
        createMethod = ReflectionUtils.findMethod(TagController.class, CREATE_METHOD_NAME, TagDto.class);

        throwIfAnyMethodNotFound(deleteMethod, readByIdMethod, createMethod);
    }

    @Override
    public void addLinks(TagDto model) {
        model.add(linkTo(deleteMethod, model.getId()).withRel(DELETE_METHOD_RELATION).withType(HttpMethod.DELETE.name()));
        model.add(linkTo(readByIdMethod, model.getId()).withRel(READ_BY_ID_METHOD_RELATION).withType(HttpMethod.GET.name()));
        model.add(linkTo(createMethod, model).withRel(CREATE_METHOD_RELATION).withType(HttpMethod.POST.name()));
    }
}
