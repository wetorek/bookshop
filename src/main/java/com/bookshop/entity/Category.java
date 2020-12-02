package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "categories_books",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<Book> booksCategory = new LinkedList<>();
    @Id
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    public void addBook(Book book) {
        this.booksCategory.add(book);
        book.getCategories().add(this);
    }

    public void removeBook(Book book) {
        this.booksCategory.remove(book);
        book.getCategories().remove(this);
    }
}
