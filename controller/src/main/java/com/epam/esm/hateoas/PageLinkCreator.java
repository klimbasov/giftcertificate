package com.epam.esm.hateoas;

import org.springframework.hateoas.PagedModel;

public interface PageLinkCreator {
    void addLinks(PagedModel<?> model);
}
