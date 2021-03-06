package com.bookshop.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "BOOKS")
public class Book {
    LocalDate dateOfRelease;
    @Id
    private Long id;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
    @NotBlank(message = "Name is required")
    private String name;
    @Min(0L)
    private Long inSock;

    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "booksAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Author> authors = new LinkedList<>();
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "booksCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Category> categories = new LinkedList<>();
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "booksPublisher", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Publisher> publishers = new LinkedList<>();


    public void removeAuthors(List<Author> authors) {
        authors.forEach(this::removeAuthor);
    }

    public void setAuthors(List<Author> authorList) {
        authors = new LinkedList<>();
        authorList.forEach(this::addAuthor);
    }

    public void setCategories(List<Category> categoryList) {
        categories = new LinkedList<>();
        categoryList.forEach(this::addCategory);
    }

    public void removeCategories(List<Category> categories) {
        categories.forEach(this::removeCategory);
    }

    public void setPublishers(List<Publisher> publisherList) {
        publishers = new LinkedList<>();
        publisherList.forEach(this::addPublisher);
    }

    public void removePublishers(List<Publisher> publishers) {
        publishers.forEach(this::removePublisher);
    }


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

    public void addPublisher(Publisher publisher) {
        this.publishers.add(publisher);
        publisher.getBooksPublisher().add(this);
    }

    public void removePublisher(Publisher publisher) {
        this.publishers.remove(publisher);
        publisher.getBooksPublisher().remove(this);
    }

}
