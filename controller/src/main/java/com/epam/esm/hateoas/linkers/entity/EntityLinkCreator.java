package com.epam.esm.hateoas.linkers.entity;

import org.springframework.hateoas.RepresentationModel;

public interface EntityLinkCreator<T extends RepresentationModel<T>> {
    void addLinks(T model);
}
