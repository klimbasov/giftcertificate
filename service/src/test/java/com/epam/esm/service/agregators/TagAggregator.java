package com.epam.esm.service.agregators;

import com.epam.esm.dao.entity.Tag;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class TagAggregator implements ArgumentsAggregator {
    @Override
    public Tag aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
        return Tag.builder()
                .id(accessor.getInteger(0))
                .name(accessor.getString(1))
                .build();
    }
}
