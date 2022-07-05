package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface EntityLinkCreator<T extends RepresentationModel<T>> {
    void addLinks(T model);
}
