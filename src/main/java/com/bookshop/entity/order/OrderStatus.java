/*
package com.bookshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Optional;

@Data
@AllArgsConstructor
@Builder
@Entity(name = "ORDER_STATUS")
public class OrderStatus {
    @Id
    private Long orderId;
    @OneToOne
    private Order order;
    @OneToOne(targetEntity = OrderState.class)
    private OrderState orderState;

    public OrderStatus() {
        this.orderState = new NewOrder(this);
    }

    public Optional<Order> createNewOrder() {
        try {
            orderState.placeNewOrder();
        } catch (Exception e) {

        }
        return Optional.empty();
    }

    public void finishOrder() {
        orderState.finishOrder();
    }

    public void payForOrder() {
        orderState.pay();
    }
}
*/
