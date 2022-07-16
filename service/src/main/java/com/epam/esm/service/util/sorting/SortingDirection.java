package com.epam.esm.service.util.sorting;

import java.util.Arrays;

public enum SortingDirection {
    INCR(Aliases.INCR),
    DECR(Aliases.DECR);

    private final String alias;

    private static final SortingDirection DEFAULT_DIRECTION = INCR;

    SortingDirection(String aliases){
        this.alias = aliases;
    }

    public static SortingDirection getSortingDirectionByAlias(String alias) {
        return Arrays.stream(SortingDirection.values())
                .filter(sortingDirection -> sortingDirection.alias.equals(alias))
                .findAny()
                .orElse(DEFAULT_DIRECTION);
    }

}

class Aliases {

    static final String INCR = "incr";
    static final String DECR = "decr";
    private Aliases() {
    }
}
