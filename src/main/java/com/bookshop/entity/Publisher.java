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
@Entity(name = "publishers")
public class Publisher {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "publishers_books",
            joinColumns = @JoinColumn(name = "publisher_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<Book> booksPublisher = new LinkedList<>();
    @Id
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    public void addBook(Book book) {
        this.booksPublisher.add(book);
        book.getPublishers().add(this);
    }

    public void removeBook(Book book) {
        this.booksPublisher.remove(book);
        book.getPublishers().remove(this);
    }

}
