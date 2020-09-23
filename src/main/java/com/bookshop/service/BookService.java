package com.bookshop.service;

import com.bookshop.entity.Book;
import com.bookshop.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book save (Book book){
        return bookRepository.save(book);
    }
}
