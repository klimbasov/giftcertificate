package com.epam.esm.service.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

import static java.util.Objects.isNull;

@Value
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
public class SearchOptions extends RepresentationModel<SearchOptions> {
    @Builder.Default
    String sorting = "";
    @Builder.Default
    String subname = "";
    @Builder.Default
    String subdescription = "";
    @Builder.Default
    Long pageNumber = 1L;
    @Builder.Default
    Long pageSize = 20L;



    public String getSorting() {
        return isNull(sorting) ? "" : sorting;
    }

    public String getSubname() {
        return isNull(subname) ? "" : subname;
    }

    public String getSubdescription() {
        return isNull(subdescription) ? "" : subdescription;
    }

    public Long getPageNumber() {
        return isNull(pageNumber) || (pageNumber <= 0) ? 1 : pageNumber;
    }

    public Long getPageSize() {
        return isNull(pageNumber) || (pageSize <= 0) ? 20 : pageNumber;
    }
}

