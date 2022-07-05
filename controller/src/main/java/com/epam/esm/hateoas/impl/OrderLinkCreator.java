package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.hateoas.EntityLinkCreator;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkCreator implements EntityLinkCreator<OrderDto> {
    @Override
    public void addLinks(OrderDto model) {
        try {
            Method deleteMethod = OrderController.class.getMethod("delete", Long.class);
            model.add(linkTo(deleteMethod, model.getId()).withRel("delete").withType("DELETE"));
        } catch (NoSuchMethodException ignored) {}
        model.add(WebMvcLinkBuilder.linkTo(methodOn(OrderController.class).readById(model.getId())).withSelfRel().withType("GET"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(OrderController.class).create(model)).withRel("create").withType("POST"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(UserController.class).readById(model.getUserId())).withRel("user").withType("GET"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CertificateController.class).readById(model.getCertificateId())).withRel("certificate").withType("GET"));
    }
}
