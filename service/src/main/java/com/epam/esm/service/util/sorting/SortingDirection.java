package com.epam.esm.service.util.sorting;

import static com.epam.esm.service.util.sorting.Aliases.isDecrAlias;
import static com.epam.esm.service.util.sorting.Aliases.isIncrAlias;

public enum SortingDirection {
    INCR,
    DECR;

    private static final SortingDirection DEFAULT_DIRECTION = INCR;

    public static SortingDirection getSortingDirectionByAlias(String alias) throws IllegalArgumentException {
        SortingDirection direction = DEFAULT_DIRECTION;
        if (isIncrAlias(alias)) {
            direction = INCR;
        }
        if (isDecrAlias(alias)) {
            direction = DECR;
        }
        return direction;
    }

}

class Aliases {

    static final String INCR = "incr";
    static final String DECR = "decr";
    private Aliases() {
    }

    static boolean isDecrAlias(String alias) {
        return INCR.equals(alias);
    }

    static boolean isIncrAlias(String alias) {
        return DECR.equals(alias);
    }
}
