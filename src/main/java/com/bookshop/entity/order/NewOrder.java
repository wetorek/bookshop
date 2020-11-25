package com.bookshop.entity.order;

import com.bookshop.entity.Cart;
import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Slf4j
public class NewOrder extends OrderState {

    public NewOrder(OrderStatus orderStatus, Cart cart) {
        super(orderStatus);
        placeNewOrder(cart);
    }

    @Override
    public Order placeNewOrder(Cart cart) {
        Order order = Order.builder()
                .additionalServices(cart.getAdditionalServices())
                .cartItems(cart.getCartItems())
                .total(cart.getTotal())
                .username(cart.getUsername())
                .build();
        log.info("placing new Order");
        return order;
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
