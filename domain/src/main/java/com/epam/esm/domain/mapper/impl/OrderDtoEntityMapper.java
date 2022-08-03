package com.epam.esm.domain.mapper.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.util.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoEntityMapper implements Mapper<Order, OrderDto> {

    private final CertificateDao certificateDao;
    private final UserDao userDao;

    @Autowired
    public OrderDtoEntityMapper(CertificateDao certificateDao, UserDao userDao) {
        this.certificateDao = certificateDao;
        this.userDao = userDao;
    }

    @Override
    public OrderDto mapToModel(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .cost(order.getCost())
                .timestamp(order.getTimestamp().toString())
                .certificateId(order.getCertificate().getId())
                .userId(order.getUser().getId())
                .build();
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        Certificate certificate = certificateDao.read(orderDto.getCertificateId()).orElse(new Certificate());
        User user = userDao.read(orderDto.getUserId()).orElse(new User());
        return Order.builder()
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .cost(certificate.getPrice())
                .certificate(certificate)
                .user(user)
                .build();
    }

    @Override
    public List<Order> mapToEntities(List<OrderDto> models) {
        return models.stream().map(this::mapToEntity).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> mapToModels(List<Order> entities) {
        return entities.stream().map(this::mapToModel).collect(Collectors.toList());
    }
}
