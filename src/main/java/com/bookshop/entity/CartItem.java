package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "cart_items")
public class CartItem {
    @Id
    private Long Id;
    private Long amountOfItems;
    @ManyToOne
    private Book book;
    private BigDecimal subTotal;
}
