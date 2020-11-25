package com.bookshop.entity.order;


import com.bookshop.entity.AdditionalService;
import com.bookshop.entity.CartItem;
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
@Entity(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String username;
    private BigDecimal total;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AdditionalService> additionalServices;
}
