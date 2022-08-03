package com.epam.esm.service.util.pagination;

import com.epam.esm.service.dto.SearchOptions;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class Pager {

    public static <T extends RepresentationModel<T>> PagedModel<T> toPage(List<T> modelList, int pageNumber, int pageSize, long totalElements) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, pageNumber, totalElements);
        return PagedModel.of(modelList, pageMetadata);
    }

    public static <T extends RepresentationModel<T>> PagedModel<T> toPage(List<T> modelList, PagedModel.PageMetadata metadata) {
        validatePageModelMetadata(metadata);
        return PagedModel.of(modelList, metadata);
    }

    private static void validatePageModelMetadata(PagedModel.PageMetadata metadata) {

    }

    public static PagedModel.PageMetadata createPageMetadata(SearchOptions options, long totalElements){
        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();

        return new PagedModel.PageMetadata(pageSize, pageNumber, totalElements);

    }

    private Pager(){}
}
