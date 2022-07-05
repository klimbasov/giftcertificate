package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.hateoas.EntityLinkCreator;
import com.epam.esm.service.dto.TagDto;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkerCreator implements EntityLinkCreator<TagDto> {
    @Override
    public void addLinks(TagDto model) {
        try {
            Method deleteMethod = TagController.class.getMethod("delete", Long.class);
            model.add(linkTo(deleteMethod, model.getId()).withRel("delete").withType("DELETE"));
        } catch (NoSuchMethodException ignored) {}
        model.add(WebMvcLinkBuilder.linkTo(methodOn(TagController.class).readById(model.getId())).withSelfRel().withType("GET"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(TagController.class).create(model)).withRel("create").withType("POST"));
    }
}
