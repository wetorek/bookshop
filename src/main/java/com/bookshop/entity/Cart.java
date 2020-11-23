package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;
    @OneToOne
    private User user;
    private BigDecimal total;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CartItem> cartItems;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AdditionalService> additionalServices;
    //discount code?
}
