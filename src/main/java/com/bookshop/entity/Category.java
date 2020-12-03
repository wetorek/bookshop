package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "CATEGORIES")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "CATEGORIES_BOOKS",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    List<Book> booksCategory = new LinkedList<>();
    @Id
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
}
