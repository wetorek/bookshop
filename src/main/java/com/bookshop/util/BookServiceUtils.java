package com.bookshop.util;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.entity.Publisher;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceUtils {

    public static boolean compareAuthors(Book book, BookDto bookDto) {
        List<Long> ids1 = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        List<Long> ids2 = bookDto.getAuthorDtoList().stream().map(AuthorDto::getId).collect(Collectors.toList());
        return compareLists(ids1, ids2);
    }

    public static boolean comparePublishers(Book book, BookDto bookDto) {
        List<Long> ids1 = book.getPublishers().stream().map(Publisher::getId).collect(Collectors.toList());
        List<Long> ids2 = bookDto.getPublisherDtoList().stream().map(PublisherDto::getId).collect(Collectors.toList());
        return compareLists(ids1, ids2);
    }

    public static boolean compareCategories(Book book, BookDto bookDto) {
        List<Long> ids1 = book.getCategories().stream().map(Category::getId).collect(Collectors.toList());
        List<Long> ids2 = bookDto.getCategoryDtoList().stream().map(CategoryDto::getId).collect(Collectors.toList());
        return compareLists(ids1, ids2);
    }

    private static boolean compareLists(List<Long> list1, List<Long> list2) {
        return list1.containsAll(list2) && list2.containsAll(list1);
    }
}
