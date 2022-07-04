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
        entityManager.persist(tag);
        return Optional.of(tag);
    }

    @Override
    public Optional<Tag> read(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> read(String name, int offset, int limit, boolean sortingDirection) {
        return entityManager.createQuery(Queries.Tag.getSelectQuery(sortingDirection), Tag.class)
                .setParameter(1, name)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public int delete(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
        removeAssociations(tag);
        return 1;
    }

    @Override
    public long count(String name) {
        return entityManager.createQuery(Queries.Tag.getCountQuery(), Long.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    private void removeAssociations(Tag tag) {
        for (Certificate certificate : tag.getCertificates()) {
            certificate.getTags().remove(tag);
        }
    }
}
