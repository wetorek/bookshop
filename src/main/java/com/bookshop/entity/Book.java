package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    LocalDate dateOfRelease;
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
    @NotBlank(message = "Name is required")
    private String name;
    //@ManyToOne
    // private Publisher publisher;
    @ManyToMany
    private List<Author> authors;
    //@OneToOne
    //private Category category;
}
