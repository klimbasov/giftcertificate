package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;;
import com.epam.esm.dao.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.nonNull;

@Repository
public class CertificateDaoImpl implements CertificateDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CertificateDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public Optional<Certificate> create(Certificate certificate, Set<Long> tagIds){
        entityManager.persist(certificate);
        return Optional.of(certificate);
    }

    @Override
    public Optional<Certificate> read(long id) {
        return Optional.of(entityManager.find(Certificate.class, id));
    }

    @Override
    public List<Certificate> read(String name, String desc, String tag, int offset, int limit) {
        CriteriaQuery<Certificate> certificateCriteriaQuery = entityManager.getCriteriaBuilder().createQuery(Certificate.class);
        Root<Certificate> root = certificateCriteriaQuery.from(Certificate.class);
        return entityManager.createQuery(certificateCriteriaQuery).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public int delete(long id) {
        int retVal = 0;
        Certificate certificate = entityManager.find(Certificate.class, id);
        if(nonNull(certificate)){
            entityManager.remove(certificate);
            retVal = 1;
        }
        return retVal;
    }

    @Override
    public void update(Certificate certificate, Set<Long> tagIds) {
    }
}
