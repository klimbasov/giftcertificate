package com.epam.esm.hateoas.linkers.page;

import org.springframework.hateoas.PagedModel;

public interface PageLinkCreator {
    void addLinks(PagedModel<?> model);
}
