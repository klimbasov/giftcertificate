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
    Integer pageNumber = 1;
    @Builder.Default
    Integer pageSize = 20;


    public String getSorting() {
        return sorting;
    }

    public String getSubname() {
        return subname;
    }

    public String getSubdescription() {
        return subdescription;
    }

    public Integer getPageNumber() {
        return isNull(pageNumber) || (pageNumber <= 0) ? 1 : pageNumber;
    }

    public Integer getPageSize() {
        return isNull(pageSize) || (pageSize <= 0) ? 20 : pageSize;
    }
}

