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
@Entity(name = "PUBLISHERS")
public class Publisher {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "PUBLISHERS_BOOKS",
            joinColumns = @JoinColumn(name = "PUBLISHER_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    List<Book> booksPublisher = new LinkedList<>();
    @Id
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;


}
