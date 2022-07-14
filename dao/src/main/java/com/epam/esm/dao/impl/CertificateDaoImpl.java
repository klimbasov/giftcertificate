package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.constant.Queries.Certificate.getCountQuery;
import static com.epam.esm.dao.constant.Queries.Certificate.getSelectSearchableQuery;

@Repository
@Transactional
public class CertificateDaoImpl implements CertificateDao {

    @PersistenceContext
    private final EntityManager manager;

    @Autowired
    public CertificateDaoImpl(EntityManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    public Optional<Certificate> create(Certificate certificate) {
        Optional<Certificate> optional = Optional.empty();
        optional = createIfDoseNotExist(certificate, optional);
        return optional;
    }

    @Override
    public Optional<Certificate> read(long id) {
        return Optional.ofNullable(manager.find(Certificate.class, id));
    }

    @Override
    public List<Certificate> read(String name, String desc, String[] tag, int offset, int limit, boolean ordering) {
        TypedQuery<Certificate> query = manager.createQuery(getSelectSearchableQuery((tag.length > 0), ordering), Certificate.class);
        setQueryParameters(name, desc, tag, query);
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public long count(String name, String desc, String[] tag) {
        TypedQuery<Long> query = manager.createQuery(getCountQuery((tag.length > 0)), Long.class);
        setQueryParameters(name, desc, tag, query);
        return query.getSingleResult();
    }

    @Override
    public int delete(long id) {
        return manager.createQuery(Queries.Certificate.getDeleteQuery())
                .setParameter(1, id)
                .executeUpdate();
    }

    @Override
    public void update(Certificate certificate) {
        manager.merge(certificate);
    }

    private Optional<Certificate> createIfDoseNotExist(Certificate certificate, Optional<Certificate> optional) {
        if (doseNotExist(certificate)) {
            optional = Optional.of(certificate);
            manager.persist(certificate);
        }
        return optional;
    }

    private boolean doseNotExist(Certificate certificate) {
        return !manager.createQuery(Queries.Certificate.getSelectByNameQuery(), Certificate.class)
                .setParameter(1, certificate.getName())
                .getResultList().stream().findAny().isPresent();
    }

    private void setQueryParameters(String name, String desc, String[] tag, Query query) {
        query.setParameter(1, name);
        query.setParameter(2, desc);
        if (tag.length > 0) {
            query.setParameter(3, Arrays.asList(tag));
            query.setParameter(4, tag.length);
        }
    }
}
