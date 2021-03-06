package com.bookshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private BigDecimal total;
    private List<CartItemDto> cartItems;
    private List<AdditionalServiceDto> additionalServices;
}
