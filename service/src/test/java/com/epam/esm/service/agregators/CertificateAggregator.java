package com.epam.esm.service.agregators;

import com.epam.esm.dao.entity.Certificate;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.time.LocalDateTime;

public class CertificateAggregator implements ArgumentsAggregator {
    @Override
    public Certificate aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
        return Certificate.builder()
                .id(accessor.getInteger(0))
                .name(accessor.getString(1))
                .description(accessor.getString(2))
                .price(accessor.getFloat(3))
                .duration(accessor.getInteger(4))
                .createDate(LocalDateTime.parse(accessor.get(5, String.class)))
                .lastUpdateDate(LocalDateTime.parse(accessor.get(6, String.class)))
                .build();
    }
}
