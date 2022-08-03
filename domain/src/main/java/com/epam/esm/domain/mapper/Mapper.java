package com.epam.esm.domain.mapper;

import java.util.List;

public interface Mapper<E, M> {
    M mapToModel(E entity);

    E mapToEntity(M model);

    List<E> mapToEntities(List<M> models);

    List<M> mapToModels(List<E> entities);
}
