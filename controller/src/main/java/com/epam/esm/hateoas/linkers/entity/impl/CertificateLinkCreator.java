package com.epam.esm.hateoas.linkers.entity.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.hateoas.linkers.entity.AbstractEntityLinkerCreator;
import com.epam.esm.service.dto.CertificateDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CertificateLinkCreator extends AbstractEntityLinkerCreator<CertificateDto> {

    private static final String DELETE_METHOD_NAME = "delete";
    private static final String UPDATE_METHOD_NAME = "patch";
    private static final String READ_BY_ID_METHOD_NAME = "readById";
    private static final String CREATE_METHOD_NAME = "create";

    private static final String DELETE_METHOD_RELATION = "delete";
    private static final String UPDATE_METHOD_RELATION = "update";
    private static final String READ_BY_ID_METHOD_RELATION = "self";
    private static final String CREATE_METHOD_RELATION = "create";

    private final Method deleteMethod;
    private final Method patchMethod;
    private final Method readByIdMethod;
    private final Method createMethod;

    public CertificateLinkCreator() {
        deleteMethod = ReflectionUtils.findMethod(CertificateController.class, DELETE_METHOD_NAME, Long.class);
        patchMethod = ReflectionUtils.findMethod(CertificateController.class, UPDATE_METHOD_NAME, CertificateDto.class, Long.class);
        readByIdMethod = ReflectionUtils.findMethod(CertificateController.class, READ_BY_ID_METHOD_NAME, Long.class);
        createMethod = ReflectionUtils.findMethod(CertificateController.class, CREATE_METHOD_NAME, CertificateDto.class);

        throwIfAnyMethodNotFound(deleteMethod, patchMethod, readByIdMethod, createMethod);
    }

    @Override
    public void addLinks(CertificateDto model) {
        model.add(linkTo(deleteMethod, model.getId()).withRel(DELETE_METHOD_RELATION).withType(HttpMethod.DELETE.name()));
        model.add(linkTo(patchMethod, model, model.getId()).withRel(UPDATE_METHOD_RELATION).withType(HttpMethod.PATCH.name()));
        model.add(linkTo(readByIdMethod, model.getId()).withRel(READ_BY_ID_METHOD_RELATION).withType(HttpMethod.GET.name()));
        model.add(linkTo(createMethod, model).withRel(CREATE_METHOD_RELATION).withType(HttpMethod.POST.name()));
    }
}
