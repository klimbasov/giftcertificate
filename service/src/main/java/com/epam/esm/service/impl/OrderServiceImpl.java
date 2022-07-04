package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectAlreadyExist;
import com.epam.esm.service.util.mapper.OrderDtoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
import static com.epam.esm.service.constant.ExceptionMessages.OBJECT_ALREADY_EXISTS;
import static com.epam.esm.service.util.validator.ArgumentValidator.OrderDtoValidator.validateCreate;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateDelete;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateRead;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderDtoEntityMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderDtoEntityMapper mapper) {
        this.orderDao = orderDao;
        this.mapper = mapper;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        validateCreate(orderDto);
        Order order = orderDao.create(mapper.mapToEntity(orderDto)).orElseThrow(() -> new ObjectAlreadyExist(OBJECT_ALREADY_EXISTS));
        return mapper.mapToDto(order);
    }

    @Override
    public OrderDto read(Long id) {
        validateRead(id);
        Order order = orderDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToDto(order);
    }

    @Override
    public PagedModel<OrderDto> read(SearchOptions options) {
        validateRead(options);
        int pageSize = options.getPageSize();
        int offset = pageSize * (options.getPageNumber() - 1);
        List<Order> orders = orderDao.read(offset, pageSize);
        long totalElements = orderDao.count();
        return PagedModel.of(mapper.mapToDto(orders), new PagedModel.PageMetadata(pageSize, options.getPageNumber(), totalElements));
    }

    @Override
    public void delete(Long id) {
        validateDelete(id);
        orderDao.delete(id);
    }
}
