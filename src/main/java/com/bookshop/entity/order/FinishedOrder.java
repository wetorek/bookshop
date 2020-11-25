package com.bookshop.entity.order;

import com.bookshop.entity.Cart;
import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Slf4j
public class FinishedOrder extends OrderState {
    public FinishedOrder(OrderStatus orderStatus) {
        super(orderStatus);
        finishOrder();
    }

    @Override
    public Order placeNewOrder(Cart cart) {
        throw new OrderChangeNotAllowedEx("Place new Order operation is not allowed: ");
    }

    @Override
    public void pay() {
        throw new OrderChangeNotAllowedEx("Pay for Order operation is not allowed: ");
    }

    @Override
    public void finishOrder() {
        log.info("finishing order");
        /*
        throw new OrderChangeNotAllowedException("Finishing Order operation is not allowed: ");*/
    }
}
