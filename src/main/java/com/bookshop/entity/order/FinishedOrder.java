package com.bookshop.entity.order;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class FinishedOrder extends OrderState {
    @OneToOne
    private OrderStatus orderStatus;

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
