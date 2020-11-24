package com.bookshop.entity.order;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class NewOrder extends OrderState {

    public NewOrder(OrderStatus orderStatus) {
        super(orderStatus);
    }

    @Override
    public Order placeNewOrder() {
        System.out.println("place order");
        return null;
    }

    @Override
    public void pay() {
        getOrderStatus().setOrderState(new PaidOrder(getOrderStatus()));
    }

    @Override
    public void finishOrder() {
        System.out.println("invalid");
    }
}
