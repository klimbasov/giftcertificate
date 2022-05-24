package com.epam.esm.service.dto;

import com.epam.esm.service.constant.SortingNames;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SearchOptions {
    @Builder.Default
    private final String SORTING = SortingNames.INCR;
    @Builder.Default
    private final String SUBNAME = "";
    @Builder.Default
    private final String SUBDESCRIPTION = "";
}
