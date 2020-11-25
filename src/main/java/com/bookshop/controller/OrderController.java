package com.bookshop.controller;

import com.bookshop.controller.dto.OrderDto;
import com.bookshop.entity.order.Order;
import com.bookshop.mapper.OrderMapper;
import com.bookshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrder() {
        Order order = orderService.createNewOrder().orElseThrow();
        return orderMapper.mapEntityTODto(order);
    }
}
