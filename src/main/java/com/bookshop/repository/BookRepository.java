package com.bookshop.repository;

import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> getBooksByAuthorsContains(Author author);
}
