package com.bookshop.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "authors")
public class Author {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<Book> booksAuthor = new LinkedList<>();
    @Id
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Second name is required")
    private String secondName;

    public void addBook(Book book) {
        this.booksAuthor.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(Book book) {
        this.booksAuthor.remove(book);
        book.getAuthors().remove(this);
    }
}
