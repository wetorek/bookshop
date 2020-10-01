package com.bookshop.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BookDto {
    private Long id;
    private BigDecimal price;
    private String name;
    private LocalDate dateOfRelease;
    private List<AuthorDto> authorDtos;
}
