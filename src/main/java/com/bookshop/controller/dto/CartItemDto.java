package com.bookshop.controller.dto;

import com.bookshop.controller.dto.book.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long amountOfItems;
    private List<BookDto> books;
    private BigDecimal subTotal;
}
