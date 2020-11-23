/*
package com.bookshop.entity.order;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class NewOrder extends OrderState {
    @OneToOne
    private OrderStatus orderStatus;

    public NewOrder(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public Order placeNewOrder() {
        System.out.println("place order");
        return null;
    }

    @Override
    public void pay() {
        orderStatus.setOrderState(new PaidOrder(orderStatus));
    }

    @Override
    public void finishOrder() {
        System.out.println("invalid");
    }
}
*/
