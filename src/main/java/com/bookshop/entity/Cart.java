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
@Entity(name = "CARTS")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;
    private String username;
    private BigDecimal total;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<CartItem> cartItems;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<AdditionalService> additionalServices;
}
