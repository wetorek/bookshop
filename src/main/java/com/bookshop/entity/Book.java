package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NonNull
    private BigDecimal price;
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn()
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @NonNull
    //@JoinTable
    private List<Author> authors;
}
