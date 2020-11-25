package com.bookshop.entity.order;

import com.bookshop.entity.Cart;
import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Slf4j
public class PaidOrder extends OrderState {

    public PaidOrder(OrderStatus orderStatus) {
        super(orderStatus);
        pay();
    }

    @Override
    public Order placeNewOrder(Cart cart) {
        throw new OrderChangeNotAllowedEx("Place new Order operation is not allowed: ");
    }

    @Override
    public void pay() {
        log.info("Paying for order!");
    }

    @Override
    public void finishOrder() {
        getOrderStatus().setOrderState(new FinishedOrder(getOrderStatus()));
    }
}
