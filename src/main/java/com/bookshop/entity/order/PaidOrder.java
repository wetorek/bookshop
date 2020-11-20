package com.bookshop.entity.order;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class PaidOrder extends OrderState{
    @OneToOne
    private OrderStatus orderStatus;

    public PaidOrder(OrderStatus orderStatus) {
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
