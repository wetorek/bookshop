package com.bookshop.entity.order;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class FinishedOrder extends OrderState {
    public FinishedOrder(OrderStatus orderStatus) {
        super(orderStatus);
    }

    @Override
    public Order placeNewOrder() {
        throw new NullPointerException();
        //return null;
    }

    @Override
    public void pay() {

    }

    @Override
    public void finishOrder() {

    }
}
