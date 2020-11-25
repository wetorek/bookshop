package com.bookshop.entity.order;

import com.bookshop.exceptions.OrderChangeNotAllowedEx;
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
        throw new OrderChangeNotAllowedEx("Place new Order operation is not allowed: ");
    }

    @Override
    public void pay() {
        System.out.println("Paying for order!");
    }

    @Override
    public void finishOrder() {
        getOrderStatus().setOrderState(new FinishedOrder(getOrderStatus()));
    }
}
