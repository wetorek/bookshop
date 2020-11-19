package com.bookshop.controller.dto.book;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BookAdminDto {
    private Long id;
    private BigDecimal price;
    private String name;
    private Long inSock;
    private LocalDate dateOfRelease;
    private List<AuthorDto> authorDtoList;
    private List<CategoryDto> categoryDtoList;
    private List<PublisherDto> publisherDtoList;
}
