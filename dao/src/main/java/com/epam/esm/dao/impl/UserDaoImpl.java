package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public UserDaoImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<User> read(long id) {
        return Optional.ofNullable(manager.find(User.class, id));
    }

    @Override
    public List<User> read(int offset, int limit, String name) {
        return manager.createQuery(Queries.User.getSelectQuery(), User.class)
                .setParameter(1, name)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long count(String name) {
        return manager.createQuery(Queries.User.getCountQuery(), Long.class)
                .setParameter(1, name)
                .getSingleResult();
    }
}
