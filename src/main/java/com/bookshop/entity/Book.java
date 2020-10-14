package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
    @NotBlank(message = "Name is required")
    private String name;
    LocalDate dateOfRelease;
    //@ManyToOne
    // private Publisher publisher;
    @ManyToMany(mappedBy = "books")
    private List<Author> authors;
    //@OneToOne
    //private Category category;
}
