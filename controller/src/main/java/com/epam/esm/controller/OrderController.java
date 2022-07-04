package com.epam.esm.controller;

import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SearchOptions;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto post(@RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<OrderDto> read(@RequestParam(required = false) Integer page) {
        SearchOptions options = SearchOptions.builder()
                .pageNumber(page)
                .build();
        return orderService.read(options);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readById(@PathVariable Long id) {
        return orderService.read(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}
