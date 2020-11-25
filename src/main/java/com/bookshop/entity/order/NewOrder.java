package com.bookshop.entity.order;

import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class NewOrder extends OrderState {

    public NewOrder(OrderStatus orderStatus) {
        super(orderStatus);
        placeNewOrder();
    }

    @Override
    public Order placeNewOrder() {
        System.out.println("placing new Order");
        return null;
    }

    @Override
    public void pay() {
        getOrderStatus().setOrderState(new PaidOrder(getOrderStatus()));
    }

    @Override
    public void finishOrder() {
        throw new OrderChangeNotAllowedEx("Finishing Order operation is not allowed: ");
    }
}
