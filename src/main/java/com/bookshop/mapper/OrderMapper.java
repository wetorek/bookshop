package com.bookshop.mapper;

import com.bookshop.controller.dto.OrderDto;
import com.bookshop.entity.order.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class OrderMapper {
    private final ModelMapper modelMapper;

    public OrderDto mapEntityTODto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
