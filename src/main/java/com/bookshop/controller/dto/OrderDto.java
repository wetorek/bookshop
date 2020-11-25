package com.bookshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long Id;
    private String username;
    private BigDecimal total;
    private List<CartItemDto> cartItems;
    private List<AdditionalServiceDto> additionalServices;
}
