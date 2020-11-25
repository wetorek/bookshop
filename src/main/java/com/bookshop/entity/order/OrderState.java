package com.bookshop.entity.order;

import com.bookshop.entity.Cart;
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

    abstract Order placeNewOrder(Cart cart);

    abstract void pay();

    abstract void finishOrder();
}
