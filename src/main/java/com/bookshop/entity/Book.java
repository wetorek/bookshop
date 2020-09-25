package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
    @NotBlank
    private String name;
    LocalDate dateOfRelease;
    @ManyToOne(fetch = FetchType.EAGER)
    private Publisher publisher;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Author> authors;
    @OneToOne
    private Category category;
}
