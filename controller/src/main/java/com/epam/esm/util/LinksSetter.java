package com.epam.esm.util;

import com.epam.esm.hateoas.linkers.entity.EntityLinkCreator;
import com.epam.esm.hateoas.linkers.page.PageLinkCreator;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

public class LinksSetter {
    private LinksSetter() {

    }

    public static <T extends RepresentationModel<T>> void setLinks(PagedModel<T> pagedModel,
                                                                   EntityLinkCreator<T> entityLinkCreator,
                                                                   PageLinkCreator pageLinkCreator) {
        for (T dto : pagedModel.getContent()) {
            entityLinkCreator.addLinks(dto);
        }
        pageLinkCreator.addLinks(pagedModel);
    }
}
