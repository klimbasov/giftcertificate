package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.hateoas.EntityLinkCreator;
import com.epam.esm.service.dto.UserDto;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkCreator implements EntityLinkCreator<UserDto> {

    @Override
    public void addLinks(UserDto model) {
        model.add(WebMvcLinkBuilder.linkTo(methodOn(UserController.class).readById(model.getId())).withSelfRel().withType("GET"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(OrderController.class).read(1, model.getId())).withRel("orders").withType("GET"));
    }
}
