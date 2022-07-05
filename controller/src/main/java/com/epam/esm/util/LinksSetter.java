package com.epam.esm.util;

import com.epam.esm.hateoas.EntityLinkCreator;
import com.epam.esm.hateoas.PageLinkCreator;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

public class LinksSetter {
    public static <T extends RepresentationModel<T>> void setLinks(PagedModel<T> pagedModel,
                                                                   EntityLinkCreator<T> entityLinkCreator,
                                                                   PageLinkCreator pageLinkCreator) {
        for (T dto : pagedModel.getContent()) {
            entityLinkCreator.addLinks(dto);
        }
        pageLinkCreator.addLinks(pagedModel);
    }

    private LinksSetter(){

    }
}
