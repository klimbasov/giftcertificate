package com.epam.esm.service.dto;

import lombok.Builder;
import lombok.Value;

import static java.util.Objects.isNull;

@Value
@Builder(toBuilder = true)
public class SearchOptions {
    @Builder.Default
    String sorting = "";
    @Builder.Default
    String subname = "";
    @Builder.Default
    String subdescription = "";


    public String getSorting() {
        return isNull(sorting) ? "" : sorting;
    }

    public String getSubname() {
        return isNull(subname) ? "" : subname;
    }

    public String getSubdescription() {
        return isNull(subdescription) ? "" : subdescription;
    }
}
