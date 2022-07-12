package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public OrderDaoImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Order> create(Order order) {
        Optional<Order> optional = Optional.empty();
        if(relatedUserAndCertificateExist(order)){
            manager.persist(order);
            optional = Optional.of(order);
        }
        return optional;
    }

    private boolean relatedUserAndCertificateExist(Order order) {
        return Stream.of(
                manager.find(Certificate.class, order.getId()),
                manager.find(User.class, order.getId())
        ).noneMatch(Objects::isNull);
    }

    @Override
    public Optional<Order> read(long id) {
        return Optional.ofNullable(manager.find(Order.class, id));
    }

    @Override
    public List<Order> read(int offset, int limit) {
        return manager.createQuery(Queries.Order.getSelectQuery(), Order.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Order> read(int offset, int limit, long userId) {
        return manager.createQuery(Queries.Order.getSelectByUserIdQuery(), Order.class)
                .setParameter(1, userId)
                .setFirstResult(offset)
                .setMaxResults(limit).getResultList();
    }

    @Override
    public int delete(long id) {
        int result = 0;
        Order order = manager.find(Order.class, id);
        if (nonNull(order)) {
            manager.remove(order);
            order.getUser().getOrders().remove(order);
            result = 1;
        }
        return result;
    }

    @Override
    public long count() {
        return manager.createQuery(Queries.Order.getCountQuery(), Long.class).getSingleResult();
    }

    @Override
    public long count(long userId) {
        return manager.createQuery(Queries.Order.getCountByUserIdQuery(), Long.class)
                .setParameter(1, userId)
                .getSingleResult();
    }
}
