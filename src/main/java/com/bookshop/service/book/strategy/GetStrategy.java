package com.bookshop.service.book.strategy;

import com.bookshop.entity.Book;

import java.util.List;

public interface GetStrategy {
    List<Book> getBooks(Long id);
}
