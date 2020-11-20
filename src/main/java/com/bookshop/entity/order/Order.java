package com.bookshop.entity.order;


import com.bookshop.entity.AdditionalService;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ORDERS")
public class Order {
    @Id
    private Long Id;
    @ManyToOne
    private User user;
    private BigDecimal total;
    @OneToMany
    private List<CartItem> cartItems;
    @OneToMany
    private List<AdditionalService> additionalServices;
}
