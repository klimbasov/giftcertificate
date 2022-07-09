package com.epam.esm.service.util.pagination;

import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class Pager {

    public static <T extends RepresentationModel<T>> PagedModel<T> toPage(List<T> modelList, int pageNumber, int pageSize, long totalElements) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, pageNumber, totalElements);
        return PagedModel.of(modelList, pageMetadata);
    }

    private Pager(){}
}
