package com.epam.esm.service.agregators;

import com.epam.esm.service.dto.CertificateDto;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.util.Arrays;

public class CertificateDtoAggregator implements ArgumentsAggregator {
    @Override
    public CertificateDto aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
        return CertificateDto.builder()
                .id(accessor.getInteger(0))
                .name(accessor.getString(1))
                .description(accessor.getString(2))
                .price(accessor.getFloat(3))
                .duration(accessor.getInteger(4))
                .createDate(accessor.get(5, String.class))
                .lastUpdateDate(accessor.get(6, String.class))
                .tags(Arrays.asList(accessor.get(7, String.class).split(", "))).build();
    }
}
