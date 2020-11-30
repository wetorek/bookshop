package com.bookshop.entity.order;

import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import com.bookshop.service.CartService;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name = "ORDER_STATE")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public abstract class OrderState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private OrderStatus orderStatus;

    public OrderState(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    Order placeNewOrder(CartService cartService) {
        throw new OrderChangeNotAllowedEx("Creating Order operation is not allowed: ");
    }

    void pay() {
        throw new OrderChangeNotAllowedEx("Paying for order operation is not allowed: ");
    }

    void finishOrder() {
        throw new OrderChangeNotAllowedEx("Finishing Order operation is not allowed: ");
    }
}
