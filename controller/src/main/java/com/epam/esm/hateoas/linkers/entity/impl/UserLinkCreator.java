package com.epam.esm.hateoas.linkers.entity.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.hateoas.linkers.entity.AbstractEntityLinkerCreator;
import com.epam.esm.service.dto.UserDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserLinkCreator extends AbstractEntityLinkerCreator<UserDto> {

    private static final String READ_BY_ID_METHOD_NAME = "readById";
    private static final String READ_RELATED_ORDERS_METHOD_NAME = "read";

    private static final String READ_BY_ID_METHOD_RELATION = "self";
    private static final String READ_RELATED_ORDERS_METHOD_RELATION = "orders";

    private final Method readByIdMethod;
    private final Method readRelatedOrdersMethod;

    public UserLinkCreator() {
        readByIdMethod = ReflectionUtils.findMethod(UserController.class, READ_BY_ID_METHOD_NAME, Long.class);
        readRelatedOrdersMethod = ReflectionUtils.findMethod(OrderController.class, READ_RELATED_ORDERS_METHOD_NAME, Integer.class, Long.class);

        throwIfAnyMethodNotFound(readByIdMethod, readRelatedOrdersMethod);
    }

    @Override
    public void addLinks(UserDto model) {
        model.add(linkTo(readByIdMethod, model.getId()).withRel(READ_BY_ID_METHOD_RELATION).withType(HttpMethod.GET.name()));
        model.add(linkTo(readRelatedOrdersMethod, 1, model.getId()).withRel(READ_RELATED_ORDERS_METHOD_RELATION).withType(HttpMethod.GET.name()));
    }
}
