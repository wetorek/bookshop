package com.bookshop.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    @NotNull
    private BigDecimal Price;
}
