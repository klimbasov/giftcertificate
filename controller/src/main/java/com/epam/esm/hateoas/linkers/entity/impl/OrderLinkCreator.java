package com.epam.esm.hateoas.linkers.entity.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.hateoas.linkers.entity.AbstractEntityLinkerCreator;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OrderLinkCreator extends AbstractEntityLinkerCreator<OrderDto> {

    //controllers method names
    private static final String DELETE_METHOD_NAME = "delete";
    private static final String READ_BY_ID_METHOD_NAME = "readById";
    private static final String CREATE_METHOD_NAME = "create";
    private static final String READ_RELATED_CERTIFICATE_METHOD_NAME = "readById";
    private static final String READ_RELATED_USER_METHOD_NAME = "readById";

    //links relations
    private static final String DELETE_METHOD_RELATION = "delete";
    private static final String READ_BY_ID_METHOD_RELATION = "self";
    private static final String CREATE_METHOD_RELATION = "create";
    private static final String READ_RELATED_CERTIFICATE_METHOD_RELATION = "certificate";
    private static final String READ_RELATED_USER_METHOD_RELATION = "user";

    private final Method deleteMethod;
    private final Method readByIdMethod;
    private final Method createMethod;
    private final Method readRelatedCertificateMethod;
    private final Method readUserOrderRelatesToMethod;

    public OrderLinkCreator(){
        deleteMethod = ReflectionUtils.findMethod(OrderController.class, DELETE_METHOD_NAME, Long.class);
        readByIdMethod = ReflectionUtils.findMethod(OrderController.class, READ_BY_ID_METHOD_NAME, Long.class);
        createMethod = ReflectionUtils.findMethod(OrderController.class, CREATE_METHOD_NAME, OrderDto.class);
        readRelatedCertificateMethod = ReflectionUtils.findMethod(CertificateController.class, READ_RELATED_CERTIFICATE_METHOD_NAME, Long.class);
        readUserOrderRelatesToMethod = ReflectionUtils.findMethod(UserController.class, READ_RELATED_USER_METHOD_NAME, Long.class);

        throwIfAnyMethodNotFound(deleteMethod, readByIdMethod,
                createMethod, readRelatedCertificateMethod,
                readUserOrderRelatesToMethod);
    }

    @Override
    public void addLinks(OrderDto model) {
        model.add(linkTo(deleteMethod, model.getId()).withRel(DELETE_METHOD_RELATION).withType(HttpMethod.DELETE.name()));
        model.add(linkTo(readByIdMethod, model.getId()).withRel(READ_BY_ID_METHOD_RELATION).withType(HttpMethod.GET.name()));
        model.add(linkTo(createMethod,model).withRel(CREATE_METHOD_RELATION).withType(HttpMethod.POST.name()));
        model.add(linkTo(readUserOrderRelatesToMethod, model.getUserId()).withRel(READ_RELATED_USER_METHOD_RELATION).withType(HttpMethod.GET.name()));
        model.add(linkTo(readRelatedCertificateMethod, model.getCertificateId()).withRel(READ_RELATED_CERTIFICATE_METHOD_RELATION).withType(HttpMethod.GET.name()));
    }
}
