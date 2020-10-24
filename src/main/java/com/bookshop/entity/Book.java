package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {
    LocalDate dateOfRelease;
    @Id
    private Long id;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
    @NotBlank(message = "Name is required")
    private String name;
    //@ManyToOne
    // private Publisher publisher;
    @ManyToMany(mappedBy = "booksAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Author> authors = new LinkedList<>();
    @ManyToMany(mappedBy = "booksCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories = new LinkedList<>();

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooksAuthor().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooksAuthor().remove(this);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBooksCategory().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getBooksCategory().remove(this);
    }

}
