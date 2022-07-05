package com.epam.esm.hateoas.impl;

import com.epam.esm.hateoas.PageLinkCreator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import static java.util.Objects.nonNull;

@Component
public class PageRequestBasedLinkCreatorImpl implements PageLinkCreator {
    @Override
    public void addLinks(PagedModel<?> model) {
        UriComponentsBuilder baseUri = getUriBuilder();
        PagedModel.PageMetadata metadata = model.getMetadata();
        if(nonNull(metadata)){
            addLinksInternal(model, baseUri, metadata);
        }
    }

    private void addLinksInternal(PagedModel<?> model, UriComponentsBuilder baseUri, PagedModel.PageMetadata metadata) {
        long pageNumber = metadata.getNumber();
        if (hasNextPage(metadata)) {
            model.add(buildPageLink(baseUri, pageNumber + 1, "next"));
        }
        if (hasPreviousPage(metadata)) {
            model.add(buildPageLink(baseUri, pageNumber - 1, "prev"));
        }
        model.add(buildPageLink(baseUri, 1, "first"));
        model.add(buildPageLink(baseUri, metadata.getTotalPages(), "last"));
    }

    private Link buildPageLink(UriComponentsBuilder baseUri, long l, String prev) {
        return Link.of(replacePageParam(baseUri, l).toUriString()).withRel(prev);
    }

    private boolean hasPreviousPage(PagedModel.PageMetadata metadata) {
        return 1 < metadata.getNumber();
    }

    private boolean hasNextPage(PagedModel.PageMetadata metadata) {
        return metadata.getTotalPages() > metadata.getNumber()
                || metadata.getNumber() < 1;
    }

    private UriComponentsBuilder getUriBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }

    private UriComponentsBuilder replacePageParam(UriComponentsBuilder origional, long page) {
        UriComponentsBuilder builder = origional.cloneBuilder();
        builder.replaceQueryParam("page", page);
        return builder;
    }
}
