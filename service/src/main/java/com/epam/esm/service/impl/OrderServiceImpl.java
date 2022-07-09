package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectAlreadyExistException;
import com.epam.esm.service.util.mapper.impl.OrderDtoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
import static com.epam.esm.service.constant.ExceptionMessages.OBJECT_ALREADY_EXISTS;
import static com.epam.esm.service.util.pagination.Pager.toPage;
import static com.epam.esm.service.util.pagination.validator.PageValidator.validate;
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
        Order order = orderDao.create(mapper.mapToEntity(orderDto)).orElseThrow(() -> new ObjectAlreadyExistException(OBJECT_ALREADY_EXISTS));
        return mapper.mapToModel(order);
    }

    @Override
    public OrderDto read(Long id) {
        validateRead(id);
        Order order = orderDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToModel(order);
    }

    @Override
    public PagedModel<OrderDto> read(SearchOptions options) {
        validateRead(options);

        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();
        int offset = pageSize * (options.getPageNumber() - 1);

        long totalElements = orderDao.count();
        validate(totalElements, pageSize, pageNumber);
        List<Order> orders = orderDao.read(offset, pageSize);
        return toPage(mapper.mapToModels(orders), pageNumber, pageSize, totalElements);
    }

    @Override
    public PagedModel<OrderDto> read(SearchOptions options, long userId) {
        validateRead(options);
        validateRead(userId);

        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();
        int offset = pageSize * (options.getPageNumber() - 1);

        long totalElements = orderDao.count(userId);
        validate(totalElements, pageSize, pageNumber);
        List<Order> orders = orderDao.read(offset, pageSize, userId);
        return toPage(mapper.mapToModels(orders), pageNumber, pageSize, totalElements);
    }

    @Override
    public void delete(Long id) {
        validateDelete(id);
        orderDao.delete(id);
    }
}
