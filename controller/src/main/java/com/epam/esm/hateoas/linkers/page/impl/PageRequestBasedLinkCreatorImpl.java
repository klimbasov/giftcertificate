package com.epam.esm.hateoas.linkers.page.impl;

import com.epam.esm.hateoas.linkers.page.PageLinkCreator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import static java.util.Objects.nonNull;

@Component
public class PageRequestBasedLinkCreatorImpl implements PageLinkCreator {

    //links relations
    private static final String NEXT_PAGE_RELATION = "next";
    private static final String PREVIOUS_PAGE_RELATION = "next";
    private static final String FIRST_PAGE_RELATION = "next";
    private static final String LAST_PAGE_RELATION = "next";

    //name of the parameter, that represents page number in request
    private static final String PAGE_URL_PARAMETER_NAME = "page";
    
    @Override
    public void addLinks(PagedModel<?> model) {
        UriComponentsBuilder baseUri = getUriBuilder();
        PagedModel.PageMetadata metadata = model.getMetadata();
        if (nonNull(metadata)) {
            addLinksInternal(model, baseUri, metadata);
        }
    }

    private void addLinksInternal(PagedModel<?> model, UriComponentsBuilder baseUri, PagedModel.PageMetadata metadata) {
        long pageNumber = metadata.getNumber();
        addIfMeetCondition(model, baseUri, hasNextPage(metadata), pageNumber + 1, NEXT_PAGE_RELATION);
        addIfMeetCondition(model, baseUri, hasPreviousPage(metadata), pageNumber - 1, PREVIOUS_PAGE_RELATION);
        model.add(buildPageLink(baseUri, 1, FIRST_PAGE_RELATION));
        model.add(buildPageLink(baseUri, metadata.getTotalPages(), LAST_PAGE_RELATION));
    }

    private void addIfMeetCondition(PagedModel<?> model, UriComponentsBuilder baseUri, boolean b, long l, String nextPageRelation) {
        if (b) {
            model.add(buildPageLink(baseUri, l, nextPageRelation));
        }
    }

    private Link buildPageLink(UriComponentsBuilder baseUri, long l, String relation) {
        return Link.of(replacePageParam(baseUri, l).toUriString()).withRel(relation);
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

    private UriComponentsBuilder replacePageParam(UriComponentsBuilder original, long page) {
        UriComponentsBuilder builder = original.cloneBuilder();
        builder.replaceQueryParam(PAGE_URL_PARAMETER_NAME, page);
        return builder;
    }
}
