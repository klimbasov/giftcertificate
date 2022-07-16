package com.epam.esm.service.util.sorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.epam.esm.service.util.sorting.SortingDirection.DECR;
import static java.util.Objects.isNull;

public class Sorter {

    private Sorter() {
    }

    public static <T> void sort(List<T> list, SortingDirection direction, Comparator<T> comparator) {
        validate(list, direction);
        list.sort(comparator);
        if (direction == DECR) {
            Collections.reverse(list);
        }
    }

    private static void validate(List<?> list, SortingDirection direction) {
        if (isNull(list) || isNull(direction)) {
            throw new IllegalArgumentException();
        }
    }
}
