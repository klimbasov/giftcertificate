package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.hateoas.EntityLinkCreator;
import com.epam.esm.service.dto.CertificateDto;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateLinkCreator implements EntityLinkCreator<CertificateDto> {

    @Override
    public void addLinks(CertificateDto model) {
        try {
            Method deleteMethod = CertificateController.class.getMethod("delete", Long.class);
            Method patchMethod = CertificateController.class.getMethod("patch", CertificateDto.class, Long.class);
            model.add(linkTo(deleteMethod, model.getId()).withRel("delete").withType("DELETE"));
            model.add(linkTo(patchMethod, model, model.getId()).withRel("patch").withType("PATCH"));
        } catch (NoSuchMethodException ignored) {}
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CertificateController.class).readById(model.getId())).withSelfRel().withType("GET"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CertificateController.class).create(model)).withRel("create").withType("POST"));
    }
}
