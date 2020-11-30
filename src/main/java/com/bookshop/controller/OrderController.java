package com.bookshop.controller;

import com.bookshop.controller.dto.OrderDto;
import com.bookshop.entity.order.Order;
import com.bookshop.mapper.OrderMapper;
import com.bookshop.repository.OrderRepository;
import com.bookshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrder() {
        Order order = orderService.createNewOrder();
        return orderMapper.mapEntityTODto(order);
    }

    @PatchMapping("/pay/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto payOrder(@PathVariable Long id) {
        Order order = orderService.payForOrder(id);
        return orderMapper.mapEntityTODto(order);
    }

    @PatchMapping("/finish/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto finishOrder(@PathVariable Long id) {
        Order order = orderService.finishOrder(id);
        return orderMapper.mapEntityTODto(order);
    }

}
