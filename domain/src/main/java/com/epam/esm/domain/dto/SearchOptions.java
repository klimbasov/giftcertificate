package com.epam.esm.domain.dto;

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
    int pageNumber = 1;
    @Builder.Default
    int pageSize = 20;


    public String getSorting() {
        return isNull(sorting) ? "" : sorting;
    }

    public String getSubname() {
        return isNull(subname) ? "" : subname;
    }

    public String getSubdescription() {
        return isNull(subdescription) ? "" : subdescription;
    }

    public Integer getPageSize() {
        return isNull(pageSize) || (pageSize <= 0) ? 20 : pageSize;
    }
}

