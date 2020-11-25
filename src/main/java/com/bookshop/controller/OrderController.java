package com.bookshop.controller;

import com.bookshop.controller.dto.OrderDto;
import com.bookshop.entity.order.Order;
import com.bookshop.mapper.OrderMapper;
import com.bookshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrder() {
        Order order = orderService.createNewOrder().orElseThrow();
        return orderMapper.mapEntityTODto(order);
    }

    @PatchMapping("/pay")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto payOrder() {
        //Order order = orderService.createNewOrder().orElseThrow();
        //return orderMapper.mapEntityTODto(order);
        return null;
    }

    @PatchMapping("/finish")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto finishOrder() {

        return null;
    }


}
