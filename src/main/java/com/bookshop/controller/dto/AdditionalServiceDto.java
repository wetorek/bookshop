package com.bookshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalServiceDto {
    private Long Id;
    private BigDecimal price;
    private BigDecimal description;
}
