package com.epam.esm.service.util.pagination.validator;

import com.epam.esm.service.exception.ext.NoSuchObjectException;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_PAGE;

public class PageValidator {
    public static void validate(long totalElements, int pageSize, int pageNum) {
        if ((long) (pageNum - 1) * pageSize >= totalElements) {
            throw new NoSuchObjectException(NO_SUCH_PAGE);
        }
    }

    private PageValidator(){}
}
