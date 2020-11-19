package com.bookshop.controller.dto.book;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private BigDecimal price;
    private String name;
    private LocalDate dateOfRelease;
    private List<AuthorDto> authorDtoList;
    private List<CategoryDto> categoryDtoList;
    private List<PublisherDto> publisherDtoList;
}
