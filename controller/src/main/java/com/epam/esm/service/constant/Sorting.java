package com.epam.esm.service.constant;

public enum Sorting {
    DESCENDING("desc"),
    ASCENDING("asc");

    private String alis;
    Sorting(final String alis){
        this.alis = alis;
    }

    public String getAlis() {
        return alis;
    }
}
