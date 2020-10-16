package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Id
    private Long id;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
    @NotBlank(message = "Name is required")
    private String name;
    LocalDate dateOfRelease;
    //@ManyToOne
    // private Publisher publisher;
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Author> authors = new LinkedList<>();
    //@OneToOne
    //private Category category;

}
