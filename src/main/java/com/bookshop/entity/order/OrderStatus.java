package com.bookshop.entity.order;

import com.bookshop.service.CartService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ORDER_STATUS")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderStatusId;
    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
    @OneToOne(targetEntity = OrderState.class, cascade = CascadeType.ALL)
    private OrderState orderState;

    public OrderStatus(CartService cartService) {
        this.orderState = new NewOrder(this);
        order = orderState.placeNewOrder(cartService);
    }


    public void finishOrder() {
        orderState.finishOrder();
    }

    public void payForOrder() {
        orderState.pay();
    }
}
