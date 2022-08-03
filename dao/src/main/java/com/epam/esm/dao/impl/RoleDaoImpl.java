package com.epam.esm.dao.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private final EntityManager manager;

    @Override
    public Optional<Role> read(long id) {
        return Optional.ofNullable(manager.find(Role.class, id));
    }

    @Override
    public List<Role> read(int offset, int limit, String name) {
        return manager.createQuery(Queries.Role.getSelectQuery(), Role.class)
                .setParameter(1, name)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long count(String name) {
        return manager.createQuery(Queries.Role.getCountQuery(), Long.class)
                .setParameter(1, name)
                .getSingleResult();
    }
}
