package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cart_items")
public class CartItem {
    @Id
    private Long Id;
    private Long amountOfItems;
    @OneToMany
    private List<Book> books;
    private BigDecimal subTotal;
}
