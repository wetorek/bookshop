package com.bookshop.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookDto {
    private Long id;
    private BigDecimal price;
    private String name;
    LocalDate dateOfRelease;
}
