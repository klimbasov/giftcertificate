package com.epam.esm.service.util.sorting;

import com.epam.esm.service.dto.SearchOptions;

import static com.epam.esm.service.util.sorting.SortingDirection.getSortingDirectionByAlias;

public class SortingUtil {
    public static boolean isSortingInverted(SearchOptions searchOptions) {
        return getSortingDirectionByAlias(searchOptions.getSorting()) == SortingDirection.INCR;
    }
}
