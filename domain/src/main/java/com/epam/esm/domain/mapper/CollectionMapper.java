package com.epam.esm.domain.mapper;

import java.util.List;

public interface CollectionMapper<E, M> {
    List<E> mapToEntities(List<M> models);

    List<M> mapToModels(List<E> entities);
}