package com.bookshop.entity.order;

import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class FinishedOrder extends OrderState {
    public FinishedOrder(OrderStatus orderStatus) {
        super(orderStatus);
        finishOrder();
    }

    @Override
    public Order placeNewOrder() {
        throw new OrderChangeNotAllowedEx("Place new Order operation is not allowed: ");
    }

    @Override
    public void pay() {
        throw new OrderChangeNotAllowedEx("Pay for Order operation is not allowed: ");
    }

    @Override
    public void finishOrder() {
        System.out.println("finishing order");
        /*
        throw new OrderChangeNotAllowedException("Finishing Order operation is not allowed: ");*/
    }
}
