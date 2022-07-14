package com.epam.esm.hateoas.linkers.entity;

import com.epam.esm.hateoas.exception.HateoasLinkingException;
import org.springframework.hateoas.RepresentationModel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractEntityLinkerCreator<T extends RepresentationModel<T>> implements EntityLinkCreator<T> {
    protected void throwIfAnyMethodNotFound(Method... methods) {
        if(Arrays.stream(methods).anyMatch(Objects::isNull)){
            throw new HateoasLinkingException("some methods were not found in provided controller class");
        }
    }
}
