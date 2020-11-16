package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cart")
public class Cart {
    @Id
    private Long cartId;
    @ManyToOne
    private User user;
    private BigDecimal total;
    @OneToMany
    private List<CartItem> cartItems;
    @OneToMany
    private List<AdditionalService> additionalServices;
    //discount code?
}
