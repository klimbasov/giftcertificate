package com.epam.esm.controller;

import com.epam.esm.hateoas.EntityLinkCreator;
import com.epam.esm.hateoas.PageLinkCreator;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.util.LinksSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final EntityLinkCreator<OrderDto> entityLinkCreator;
    private final PageLinkCreator pageLinkCreator;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService,
                           EntityLinkCreator<OrderDto> entityLinkCreator,
                           PageLinkCreator pageLinkCreator
    ) {
        this.entityLinkCreator = entityLinkCreator;
        this.pageLinkCreator = pageLinkCreator;
        this.orderService = orderService;
    }

    @PostMapping("order/")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody OrderDto dto) {
        OrderDto model = orderService.create(dto);
        entityLinkCreator.addLinks(model);
        return model;
    }

    @GetMapping("order/")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<OrderDto> read(@RequestParam(required = false) Integer page) {
        SearchOptions options = SearchOptions.builder()
                .pageNumber(page)
                .build();
        PagedModel<OrderDto> model = orderService.read(options);
        LinksSetter.setLinks(model, entityLinkCreator, pageLinkCreator);
        return model;
    }

    @GetMapping("order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readById(@PathVariable Long id) {
        OrderDto model = orderService.read(id);
        entityLinkCreator.addLinks(model);
        return model;
    }

    @DeleteMapping("order/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    @GetMapping("/user/{userId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<OrderDto> read(@RequestParam(required = false) Integer page, @PathVariable Long userId) {
        SearchOptions options = SearchOptions.builder()
                .pageNumber(page)
                .build();
        PagedModel<OrderDto> model = orderService.read(options);
        LinksSetter.setLinks(model, entityLinkCreator, pageLinkCreator);
        return model;
    }
}
