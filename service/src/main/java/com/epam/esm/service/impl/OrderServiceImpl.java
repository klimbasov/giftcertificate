package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.impl.OrderDtoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMessages.*;
import static com.epam.esm.service.util.pagination.Pager.toPage;
import static com.epam.esm.service.util.pagination.validator.PageValidator.validate;
import static com.epam.esm.service.util.validator.ArgumentValidator.OrderDtoValidator.validateCreate;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateDelete;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateRead;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao dao;
    private final OrderDtoEntityMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderDtoEntityMapper mapper) {
        this.dao = orderDao;
        this.mapper = mapper;
    }

    @Override
    public OrderDto create(OrderDto model) {
        validateCreate(model);
        Order entity = dao.create(mapper.mapToEntity(model)).orElseThrow(() -> new NoSuchObjectException(USER_OR_CERTIFICATE_DOSE_NOT_EXIST));
        return mapper.mapToModel(entity);
    }

    @Override
    public OrderDto read(Long id) {
        validateRead(id);
        Order entity = dao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToModel(entity);
    }

    @Override
    public PagedModel<OrderDto> read(SearchOptions options) {
        validateRead(options);

        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();
        int offset = pageSize * (options.getPageNumber() - 1);

        long totalElements = dao.count();
        validate(totalElements, pageSize, pageNumber);
        List<Order> orders = dao.read(offset, pageSize);
        return toPage(mapper.mapToModels(orders), pageNumber, pageSize, totalElements);
    }

    @Override
    public PagedModel<OrderDto> read(SearchOptions options, long userId) {
        validateRead(options);
        validateRead(userId);

        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();
        int offset = pageSize * (options.getPageNumber() - 1);

        long totalElements = dao.count(userId);
        validate(totalElements, pageSize, pageNumber);
        List<Order> entities = dao.read(offset, pageSize, userId);
        return toPage(mapper.mapToModels(entities), pageNumber, pageSize, totalElements);
    }

    @Override
    public void delete(Long id) {
        validateDelete(id);
        throwIfNoEffect(dao.delete(id));
    }

    private void throwIfNoEffect(int modifiedLines) {
        if (modifiedLines == 0) {
            throw new NoSuchObjectException(NO_SUCH_OBJECT);
        }
    }
}
