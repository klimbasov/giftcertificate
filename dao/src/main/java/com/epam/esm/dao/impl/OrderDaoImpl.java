package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.entity.Order;
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
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public OrderDaoImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Order> create(Order order) {
        manager.persist(order);
        return Optional.of(order);
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
}
