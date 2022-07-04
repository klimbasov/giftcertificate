package com.epam.esm.service.util.mapper;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoEntityMapper {

    private final CertificateDao certificateDao;
    private final UserDao userDao;

    @Autowired
    public OrderDtoEntityMapper(CertificateDao certificateDao, UserDao userDao) {
        this.certificateDao = certificateDao;
        this.userDao = userDao;
    }

    public OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .cost(order.getCost())
                .timestamp(order.getTimestamp().toString())
                .certificateId(order.getCertificate().getId())
                .userId(order.getUser().getId())
                .build();
    }

    public List<OrderDto> mapToDto(List<Order> orders) {
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Order mapToEntity(OrderDto orderDto) {
        Certificate certificate = certificateDao.read(orderDto.getCertificateId()).orElse(new Certificate());
        User user = userDao.read(orderDto.getUserId()).orElse(new User());
        return Order.builder()
                .timestamp(LocalDateTime.now())
                .cost(certificate.getPrice())
                .certificate(certificate)
                .user(user)
                .build();
    }
}
