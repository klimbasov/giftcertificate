package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tag> create(Tag tag) {
        return createIfDoesNotExist(tag);
    }

    @Override
    public Optional<Tag> read(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> read(String name, int offset, int limit, boolean ordering) {
        return entityManager.createQuery(Queries.Tag.getSelectQuery(ordering), Tag.class)
                .setParameter(1, name)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public int delete(long id) {
        int retVal = 0;
        Tag tag = entityManager.find(Tag.class, id);
        if (nonNull(tag)) {
            entityManager.remove(tag);
            removeAssociations(tag);
            retVal = 1;
        }
        return retVal;
    }

    @Override
    public long count(String name) {
        return entityManager.createQuery(Queries.Tag.getCountQuery(), Long.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    @Override
    public Optional<Tag> readMostUsedTagOfUserWithHighestOrderCost() {
        return Optional.ofNullable((Tag) entityManager.createNativeQuery(Queries.Tag.getComplexSelectQuery(), Tag.class).getSingleResult());
    }

    private Optional<Tag> createIfDoesNotExist(Tag tag) {
        Optional<Tag> optional = Optional.empty();
        if (doseNotExist(tag)) {
            entityManager.persist(tag);
            optional = Optional.of(tag);
        }
        return optional;
    }

    private boolean doseNotExist(Tag tag) {
        return !entityManager.createQuery(Queries.Tag.getSelectQuery(false))
                .setParameter(1, tag.getName())
                .getResultList().stream().findAny()
                .isPresent();
    }

    private void removeAssociations(Tag tag) {
        for (Certificate certificate : tag.getCertificates()) {
            certificate.getTags().remove(tag);
        }
    }
}
