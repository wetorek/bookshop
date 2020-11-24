package com.bookshop.entity.order;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class PaidOrder extends OrderState {

    public PaidOrder(OrderStatus orderStatus) {
        super(orderStatus);
        pay();
        // this.orderStatus = orderStatus;
    }

    @Override
    public Order placeNewOrder() {
        return null;
    }

    @Override
    public void pay() {

    }

    @Override
    public void finishOrder() {

    }
}
