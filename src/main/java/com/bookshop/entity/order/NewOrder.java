package com.bookshop.entity.order;

import com.bookshop.entity.Cart;
import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import com.bookshop.service.CartService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@NoArgsConstructor
@Slf4j
public class NewOrder extends OrderState {

    @Transient
    private boolean placed = false;

    public NewOrder(OrderStatus orderStatus) {
        super(orderStatus);
    }

    @Override
    public Order placeNewOrder(CartService cartService) {
        if (placed) {
            throw new OrderChangeNotAllowedEx("This operation is not allowed");
        }
        Cart cart = cartService.getCart();
        Order order = Order.builder()
                .additionalServices(cart.getAdditionalServices())
                .cartItems(cart.getCartItems())
                .total(cart.getTotal())
                .username(cart.getUsername())
                .build();
        cartService.emptyCart();
        log.info("Placing new Order");
        placed = true;
        return order;
    }

    @Override
    public void pay() {
        getOrderStatus().setOrderState(new PaidOrder(getOrderStatus()));
    }
}
